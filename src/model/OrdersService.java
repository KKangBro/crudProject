package model;

import java.text.DecimalFormat;
import java.util.List;

import vo.OrdersJoinMenuVO;
import vo.OrdersVO;

public class OrdersService {
	OrdersDAO ordersDao = new OrdersDAO();
	DecimalFormat formatter = new DecimalFormat("###,###");

	// 해당 회원의 주문 내역 조회
	public List<OrdersJoinMenuVO> selectOrdersByCustomerId(String cust_id) {
		return ordersDao.selectOrdersByCustomerId(cust_id);
	}

	// 주문 추가 (insert)
	public String insertOrders(OrdersVO orders) {
		String message = null;
		int rst = ordersDao.insertOrders(orders);
		if (rst > 0) {
			message = ", " + orders.getQuantity() + "건 주문이 정상적으로 처리되었습니다.\n총 결제 금액은 "
					+ formatter.format(orders.getOverall()) + " 원 입니다. Enter..";
		} else {
			message = "ERROR";
		}
		return message;
	}

	// ----------------------------------------------

	// [관리자 모드] 총 매출 조회
	public int selectAllSumOfOverall() {
		return ordersDao.selectAllSumOfOverall();
	}

	// [관리자 모드] 모든 주문 내역 조회
	public List<OrdersJoinMenuVO> selectAllOrders() {
		return ordersDao.selectAllOrders();
	}
}
