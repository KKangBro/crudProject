package view;

import vo.CustomerVO;

public class CustomerView {
	public static void print(CustomerVO cust) {
		MainView.nextPage();
		System.out.println("-----------회원 정보-----------");
		if (cust == null) {
			print("존재하지 않는 회원입니다.");
		} else {
			System.out.print(cust);
		}
	}

	public static void print(String message) {
		System.out.println("[알림] " + message);
	}
}
