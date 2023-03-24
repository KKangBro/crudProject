package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.MenuService;
import model.OrdersService;
import view.MainView;
import view.MenuView;
import view.OrdersView;
import vo.MenuVO;

public class AdminController {
	public void adminMode(Scanner sc, MenuService menuService, OrdersService ordersService) {
		while (true) {
			MainView.nextPage();
			System.out.println("[관리자 모드]\n");
			System.out.println("1. 메뉴 관리");
			System.out.println("2. 주문 관리");
			System.out.println("q. 종료");
			System.out.print(":: 작업을 선택해주세요>> ");
			String input = sc.nextLine();

			if (input.equals("q") || input.equals("Q")) // 종료
				return;

			switch (input) {
			case "1": { // 메뉴 관리
				while (true) {
					MainView.nextPage();
					System.out.println("1. 메뉴 보기");
					System.out.println("2. 메뉴 추가");
					System.out.println("3. 메뉴 수정");
					System.out.println("4. 메뉴 삭제");
					System.out.println("5. 입고 처리");
					System.out.println("q. 뒤로가기");
					System.out.print(":: 작업을 선택해주세요>> ");
					input = sc.nextLine();

					if (input.equals("q") || input.equals("Q")) { // 뒤로가기
						break;
					}

					switch (input) {
					case "1": { // 메뉴 보기
						MainView.nextPage();
						MenuView.printForAdmin(menuService.selectAllMenuOrderBy(">=", "menu_id", "asc"));
						break;
					}
					case "2": { // 메뉴 추가
						List<MenuVO> menuList = menuService.selectAllMenuOrderBy(">=", "menu_id", "asc");
						Set<String> nameSet = new HashSet<>();
						for (MenuVO menu : menuList) {
							nameSet.add(menu.getMenu_name());
						}

						while (true) {
							MainView.nextPage();
							System.out.print("추가할 메뉴 이름>> ");
							String menu_name = sc.nextLine();

							if (nameSet.contains(menu_name)) {
								System.out.println("해당 메뉴는 이미 메뉴판에 존재합니다.");
							} else {
								System.out.print("추가할 메뉴 가격>> ");
								int price = Integer.parseInt(sc.nextLine());

								MenuView.print(menuService.insertMenu(menu_name, price));
								sc.nextLine();
								break;
							}
						}
						break;
					}
					case "3": { // 메뉴 수정
						List<MenuVO> menuList = menuService.selectAllMenuOrderBy(">=", "menu_id", "asc");
						Set<Integer> idSet = new HashSet<>();
						for (MenuVO menu : menuList) {
							idSet.add(menu.getMenu_id());
						}

						MainView.nextPage();
						MenuView.printForAdmin(menuList); // 수정하기 전에 모든 메뉴 보여준다
						while (true) {
							System.out.print(":: 수정할 메뉴의 ID를 입력해주세요>> ");
							int menu_id = Integer.parseInt(sc.nextLine());

							if (idSet.contains(menu_id)) {
								MenuVO updateMenu = makeMenu(sc, menu_id);
								MenuView.print(menuService.updateMenu(updateMenu));
								sc.nextLine();
								break;
							} else {
								System.out.println("해당 메뉴는 존재하지 않습니다. 다시 입력해주세요..\n");
							}
						}
						break;
					}
					case "4": { // 메뉴 삭제
						List<MenuVO> menuList = menuService.selectAllMenuOrderBy(">=", "menu_id", "asc");
						Set<Integer> idSet = new HashSet<>();
						for (MenuVO menu : menuList) {
							idSet.add(menu.getMenu_id());
						}
						
						MainView.nextPage();
						MenuView.printForAdmin(menuList); // 삭제하기 전에 모든 메뉴 보여준다

						deleteMenu: while (true) {
							System.out.print(":: 삭제할 메뉴의 ID를 입력해주세요>> ");
							int menu_id = Integer.parseInt(sc.nextLine());

							if (idSet.contains(menu_id)) {
								while (true) {
									System.out.print("정말로 삭제하시겠습니까? [Y/N]\t");
									String yesno = sc.nextLine();
									if (yesno.equals("y") || yesno.equals("Y")) {
										MenuView.print(menuService.deleteMenu(menu_id));
										sc.nextLine();
										break deleteMenu;
									} else if (yesno.equals("n") || yesno.equals("N")) {
										break deleteMenu;
									} else {
										System.out.println("Y/N 중에 입력해주세요!\n");
									}
								}
							} else {
								System.out.println("해당 메뉴는 존재하지 않습니다. 다시 입력해주세요..\n");
							}
						}
						break;
					}
					case "5": { // 입고 처리
						List<MenuVO> menuList = menuService.selectAllMenuOrderBy(">=", "menu_id", "asc");
						Set<Integer> idSet = new HashSet<>();
						for (MenuVO menu : menuList) {
							idSet.add(menu.getMenu_id());
						}

						MainView.nextPage();
						MenuView.printForAdmin(menuList); // 처리 전에 모든 메뉴 보여준다
						while (true) {
							System.out.print(":: 입고 처리할 메뉴의 ID를 입력해주세요>> ");
							int menu_id = Integer.parseInt(sc.nextLine());

							if (idSet.contains(menu_id)) {
								int stock = menuService.selectByMenuId(menu_id).getStock();
								System.out.print("입고 수량>> ");
								stock += Integer.parseInt(sc.nextLine());
								MenuView.print(menuService.updateStock(menu_id, stock));
								sc.nextLine();
								break;
							} else {
								System.out.println("해당 메뉴는 존재하지 않습니다. 다시 입력해주세요..\n");
							}
						}
						break;
					}
					default:
						System.out.println("다시 입력해주세요..");
					}
				}
				break;
			}
			case "2": { // 주문 관리
				while (true) {
					MainView.nextPage();
					System.out.println("1. 모든 주문 내역 조회");
					System.out.println("2. 총 매출 조회");
					System.out.println("q. 뒤로가기");
					System.out.print(":: 작업을 선택해주세요>> ");
					input = sc.nextLine();

					if (input.equals("q") || input.equals("Q")) { // 뒤로가기
						break;
					}

					switch (input) {
					case "1": { // 모든 주문 내역 조회
						OrdersView.printForAdmin(ordersService.selectAllOrders());
						break;
					}
					case "2": { // 총 매출 조회
						OrdersView.print(ordersService.selectAllSumOfOverall());
						break;
					}
					default:
						System.out.println("다시 입력해주세요..");
					}
				}
				break;
			}
			default:
				System.out.println("다시 입력해주세요..");
			}
		}
	}

	private static MenuVO makeMenu(Scanner sc, int menu_id) {
		System.out.print("메뉴명>>\t");
		String menu_name = sc.nextLine();
		System.out.print("가격>>\t");
		int price = Integer.parseInt(sc.nextLine());

		MenuVO menu = new MenuVO();
		menu.setMenu_id(menu_id);
		menu.setMenu_name(menu_name);
		menu.setPrice(price);

		return menu;
	}
}
