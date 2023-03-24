package view;

import java.text.DecimalFormat;
import java.util.List;

import vo.OrdersJoinMenuVO;

public class OrdersView {
	public static void print(List<OrdersJoinMenuVO> ojmList) {
		MainView.nextPage();
		if (ojmList.size() == 0) {
			MainView.print("주문 내역이 없습니다.");
		} else {
			System.out.println("--------------------------------주문 내역--------------------------------");
			for (OrdersJoinMenuVO ojm : ojmList) {
				System.out.println(ojm);
			}
		}
	}
	
	public static void printForAdmin(List<OrdersJoinMenuVO> ojmList) {
		MainView.nextPage();
		if (ojmList.size() == 0) {
			MainView.print("주문 내역이 없습니다.");
		} else {
			System.out.println("--------------------------------주문 내역--------------------------------");
			for (OrdersJoinMenuVO ojm : ojmList) {
				System.out.println(ojm.toStringForAdmin());
			}
		}
	}
	
	public static void print(int sumOfOverall) {
		DecimalFormat formatter = new DecimalFormat("###,###");
		System.out.println("\n총 매출은 " + formatter.format(sumOfOverall) + " 원 입니다.");
	}

	public static void print(String message) {
		System.out.println(message);
	}
}
