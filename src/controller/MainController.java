package controller;

import java.util.Scanner;
import java.util.Set;

import view.MainView;
import vo.CustomerVO;
import model.AdminService;
import model.CustomerService;
import model.MenuService;
import model.OrdersService;

public class MainController {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AdminController adminController = new AdminController();
		LoginController loginController = new LoginController();
		AdminService adminService = new AdminService();
		CustomerService customerService = new CustomerService();
		MenuService menuService = new MenuService();
		OrdersService ordersService = new OrdersService();

		while (true) {
			MainView.nextPage();
			System.out.println("===--- 사이렌 오더 ---===\n");
			System.out.println("1. 로그인");
			System.out.println("2. 회원 가입");
			System.out.println("q. 종료");
			System.out.print(":: 작업을 선택해주세요>> ");
			String input = sc.nextLine();

			if (input.equals("q") || input.equals("Q"))
				break;

			switch (input) {
			case "1": { // 로그인
				boolean login = false;
				String message = null;

				System.out.print("\nID>>\t");
				String id = sc.nextLine();
				System.out.print("PW>>\t");
				String pw = sc.nextLine();

				CustomerVO cust = customerService.selectByCustomerId(id);

				if (cust != null) {
					if (cust.getCustomer_pw().equals(pw)) { // customer login
						System.out.print(cust.getCustomer_name() + " 님! 반갑습니다 ^^7");
						login = true;
					} else {
						message = "비밀번호가 올바르지 않습니다. Enter..";
					}
				} else {
					String admin_pw = adminService.selectByAdminId(id);
					if (admin_pw != null && admin_pw.equals(pw)) {
						adminController.adminMode(sc, menuService, ordersService); // admin login
					} else {
						message = "아이디가 올바르지 않습니다. Enter..";
					}
				}
				
				if (message != null) {
					MainView.print(message);
					sc.nextLine();
				}

				loginController.login(login, cust, sc, menuService, ordersService, customerService); // 고객 로그인 후 내부 로직
				break;
			}
			case "2": { // 회원 가입
				while (true) {
					CustomerVO cust = makeCust(sc, customerService, adminService);
					if (cust == null) {
						System.out.println("해당 아이디는 사용할 수 없습니다.");
						MainView.nextPage();
					} else {
						MainView.print(customerService.insertCust(cust));
						sc.nextLine();
						break;
					}
				}
				break;
			}
			default:
				System.out.println("다시 입력해주세요..");
			}
		}
		System.out.println("다음에 또 만나요 ^^7");
		sc.close();
	}

	private static CustomerVO makeCust(Scanner sc, CustomerService customerService, AdminService adminService) {
		System.out.print("\nID>>\t");
		String id = sc.nextLine();

		Set<String> custIdSet = customerService.selectAllCustomerId();
		Set<String> adminIdSet = adminService.selectAllAdminId();
		if (custIdSet.contains(id) || adminIdSet.contains(id)) { // 아이디 중복 체크
			return null;
		}

		System.out.print("PW>>\t");
		String pw = sc.nextLine();
		System.out.print("이름>>\t");
		String name = sc.nextLine();
		System.out.print("생년월일(yymmdd)>>\t");
		int birth = Integer.parseInt(sc.nextLine());
		System.out.print("휴대전화>>\t");
		String phone = sc.nextLine();

		CustomerVO cust = new CustomerVO();
		cust.setCustomer_id(id);
		cust.setCustomer_pw(pw);
		cust.setCustomer_name(name);
		cust.setBirth(birth);
		cust.setPhone_number(phone);

		return cust;
	}

}
