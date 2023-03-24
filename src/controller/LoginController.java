package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.CustomerService;
import model.MenuService;
import model.OrdersService;
import view.CustomerView;
import view.MainView;
import view.MenuView;
import view.OrdersView;
import vo.CustomerVO;
import vo.MenuVO;
import vo.OrdersVO;

public class LoginController {

	public void login(boolean login, CustomerVO cust, Scanner sc, MenuService menuService, OrdersService ordersService,
			CustomerService customerService) {
		while (login) {
			MainView.nextPage();
			System.out.println("1. 주문");
			System.out.println("2. 마이페이지");
			System.out.println("q. 로그아웃");
			System.out.print(":: 작업을 선택해주세요>> ");
			String input = sc.nextLine();

			if (input.equals("q") || input.equals("Q")) { // 로그아웃
				System.out.println("로그아웃 되었습니다. Enter..");
				sc.nextLine();
				return;
			}

			switch (input) {
			case "1": { // 주문
				String colName = "menu_id";
				String ordeyBy = "asc";
				List<MenuVO> menuList = menuService.selectAllMenuOrderBy(">", colName, ordeyBy);
				Set<Integer> idSet = new HashSet<>();
				for (MenuVO menuVo : menuList) {
					idSet.add(menuVo.getMenu_id());
				}

				while (true) {
					MainView.nextPage();
					System.out.println("==================================================");
					System.out.print("z. 메뉴명 순\t");
					System.out.print("x. 메뉴명 역순\t");
					System.out.println("c. 판매량 순");
					System.out.print("v. 낮은 가격 순\t");
					System.out.print("b. 높은 가격 순\t");
					System.out.println("q. 뒤로가기");
					System.out.println("==================================================");

					MenuView.print(menuService.selectAllMenuOrderBy(">", colName, ordeyBy));
					System.out.print(":: 주문하실 메뉴의 번호를 입력해주세요>> ");
					input = sc.nextLine();

					if (input.equals("q") || input.equals("Q")) { // 뒤로가기
						break;
					}

					if (isNumeric(input)) {
						int menu_id = Integer.parseInt(input);
						if (idSet.contains(menu_id)) {
							MenuVO menu = menuService.selectByMenuId(menu_id);
							OrdersVO order = makeOrder(sc, cust.getCustomer_id(), menu);
							
							if (order == null) {
								System.out.println("재고를 초과하여 주문할 수 없습니다. Enter..");
								sc.nextLine();
							} else {
								System.out.print(menu.getMenu_name());
								OrdersView.print(ordersService.insertOrders(order));
								sc.nextLine();
								break;
							}
						} else {
							System.out.println("해당 메뉴는 존재하지 않습니다.");
							System.out.println("다시 입력해주세요. Enter..");
							sc.nextLine();
						}

					} else {
						switch (input) {
						case "z": { // 메뉴명 순
							colName = "menu_name";
							ordeyBy = "asc";
							break;
						}
						case "x": { // 메뉴명 역순
							colName = "menu_name";
							ordeyBy = "desc";
							break;
						}
						case "c": { // 판매량 순
							colName = "sales";
							ordeyBy = "desc";
							break;
						}
						case "v": { // 낮은 가격 순
							colName = "price";
							ordeyBy = "asc";
							break;
						}
						case "b": { // 높은 가격 순
							colName = "price";
							ordeyBy = "desc";
							break;
						}
						default:
							System.out.println("다시 입력해주세요..");
						}
					}
				}
				break;
			}
			case "2": { // 마이페이지
				while (true) {
					cust = customerService.selectByCustomerId(cust.getCustomer_id());
					MainView.nextPage();
					System.out.println(cust.getCustomer_name() + " 님의 마이페이지\n---------------------------");
					System.out.println("1. 주문 내역");
					System.out.println("2. 회원 정보");
					System.out.println("3. 회원 정보 수정");
					System.out.println("4. 회원 탈퇴");
					System.out.println("q. 뒤로가기");
					System.out.print(":: 작업을 선택해주세요>> ");
					input = sc.nextLine();

					if (input.equals("q") || input.equals("Q")) { // 뒤로가기
						break;
					}

					switch (input) {
					case "1": { // 주문 내역
						OrdersView.print(ordersService.selectOrdersByCustomerId(cust.getCustomer_id()));
						break;
					}
					case "2": { // 회원 정보
						CustomerView.print(customerService.selectByCustomerId(cust.getCustomer_id()));
						break;
					}
					case "3": { // 회원 정보 수정
						CustomerView.print(customerService.selectByCustomerId(cust.getCustomer_id()));
						CustomerVO updateCust = makeCust(sc, cust.getCustomer_id());
						CustomerView.print(customerService.updateCust(updateCust));
						sc.nextLine();
						break;
					}
					case "4": { // 회원 탈퇴
						while (true) {
							System.out.print("정말로 탈퇴하시겠습니까? [Y/N]\t");
							String yesno = sc.nextLine();
							if (yesno.equals("y") || yesno.equals("Y")) {
								CustomerView.print(customerService.deleteCust(cust.getCustomer_id()));
								sc.nextLine();
								return;
							} else if (yesno.equals("n") || yesno.equals("N")) {
								break;
							} else {
								System.out.println("Y/N 중에 입력해주세요!\n");
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
			default:
				System.out.println("다시 입력해주세요..");
			}
		}
	}

	private static OrdersVO makeOrder(Scanner sc, String customer_id, MenuVO menu) {
		System.out.print("수량>> ");
		int quantity = Integer.parseInt(sc.nextLine());
		if (quantity > menu.getStock()) {
			return null;
		}

		OrdersVO orders = new OrdersVO();
		orders.setCustomer_id(customer_id);
		orders.setMenu_id(menu.getMenu_id());
		orders.setQuantity(quantity);
		orders.setOverall(menu.getPrice() * quantity);

		return orders;
	}

	private static CustomerVO makeCust(Scanner sc, String custId) {
		System.out.print("\nPW>>\t");
		String pw = sc.nextLine();
		System.out.print("이름>>\t");
		String name = sc.nextLine();
		System.out.print("생년월일(yymmdd)>>\t");
		int birth = Integer.parseInt(sc.nextLine());
		System.out.print("휴대전화>>\t");
		String phone = sc.nextLine();

		CustomerVO cust = new CustomerVO();
		cust.setCustomer_id(custId);
		cust.setCustomer_pw(pw);
		cust.setCustomer_name(name);
		cust.setBirth(birth);
		cust.setPhone_number(phone);

		return cust;
	}

	private static boolean isNumeric(String str) {
		return str != null && str.matches("[0-9.]+");
	}

}
