package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.OracleUtil;
import vo.OrdersJoinMenuVO;
import vo.OrdersVO;

public class OrdersDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	int rstCount;

	// 해당 회원의 주문 내역 조회
	public List<OrdersJoinMenuVO> selectOrdersByCustomerId(String cust_id) {
		List<OrdersJoinMenuVO> ojmList = new ArrayList<>();
		String sql = """
				select o.order_id, m.menu_name, o.quantity, o.overall
				from orders o join menu m using(menu_id)
				where customer_id = ?
				order by 1 desc
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cust_id);
			rs = pst.executeQuery();

			while (rs.next()) {
				OrdersJoinMenuVO ojm = makeOjm(rs);
				ojmList.add(ojm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return ojmList;
	}

	// 주문 추가 (insert)
	public int insertOrders(OrdersVO orders) {
		String sql = """
				insert into orders values(sep_order_id.nextval, ?, ?, ?, ? )
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, orders.getCustomer_id());
			pst.setInt(2, orders.getMenu_id());
			pst.setInt(3, orders.getQuantity());
			pst.setInt(4, orders.getOverall());
			rstCount = pst.executeUpdate();

		} catch (SQLException e) {
			rstCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, pst, conn);
		}

//		System.out.println("insert결과: " + rstCount);
		return rstCount;
	}

	// ----------------------------------------------

	// [관리자 모드] 총 매출 조회
	public int selectAllSumOfOverall() {
		int totalSales = 0;
		String sql = """
				select overall from orders
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				totalSales += rs.getInt("overall");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return totalSales;
	}

	// [관리자 모드] 모든 주문 내역 조회
	public List<OrdersJoinMenuVO> selectAllOrders() {
		List<OrdersJoinMenuVO> ojmList = new ArrayList<>();
		String sql = """
				select o.order_id, nvl(o.customer_id, '탈퇴한 회원') customer_id, m.menu_name, o.quantity, o.overall
				from orders o join menu m using(menu_id)
				order by 1 desc
				""";

		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				OrdersJoinMenuVO ojm = makeOjm(rs);
				ojm.setCustomer_id(rs.getString("customer_id"));
				ojmList.add(ojm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return ojmList;
	}

	// OrdersJoinMenuVO의 객체 만드는 함수
	private OrdersJoinMenuVO makeOjm(ResultSet rs) throws SQLException {
		OrdersJoinMenuVO ojm = new OrdersJoinMenuVO();
		ojm.setOrder_id(rs.getInt("order_id"));
		ojm.setMenu_name(rs.getString("menu_name"));
		ojm.setQuantity(rs.getInt("quantity"));
		ojm.setOverall(rs.getInt("overall"));

		return ojm;
	}

}
