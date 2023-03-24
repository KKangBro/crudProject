package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import util.OracleUtil;
import vo.CustomerVO;

public class CustomerDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	int rstCount;

	// 모든 customer의 customer_id 조회
	public Set<String> selectAllCustomerId() {
		Set<String> set = new HashSet<>();
		String sql = """
				select customer_id
				from customer
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				set.add(rs.getString("customer_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return set;
	}

	// customer_id로 특정 customer 조회
	public CustomerVO selectByCustomerId(String cust_id) {
		CustomerVO cust = null;
		String sql = """
				select *
				from customer
				where customer_id = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cust_id);
			rs = pst.executeQuery();

			if (rs.next()) {
				cust = makeCust(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return cust;
	}

	// 회원 가입 (insert)
	public int insertCust(CustomerVO cust) {
		String sql = """
				insert into customer
				values( ?, ?, ?, ?, ? )
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cust.getCustomer_id());
			pst.setString(2, cust.getCustomer_pw());
			pst.setString(3, cust.getCustomer_name());
			pst.setInt(4, cust.getBirth());
			pst.setString(5, cust.getPhone_number());

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

	// 회원 정보 수정 (update)
	public int updateCust(CustomerVO cust) {
		String sql = """
				update customer
				set customer_pw = ?, customer_name = ?, birth = ?, phone_number = ?
				where customer_id = ?
				""";

		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cust.getCustomer_pw());
			pst.setString(2, cust.getCustomer_name());
			pst.setInt(3, cust.getBirth());
			pst.setString(4, cust.getPhone_number());
			pst.setString(5, cust.getCustomer_id());

			rstCount = pst.executeUpdate();

		} catch (SQLException e) {
			rstCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, pst, conn);
		}

//		System.out.println("update결과: " + rstCount);
		return rstCount;
	}

	// 회원 탈퇴 (delete)
	public int deleteCust(String cust_id) {
		String sql = """
				delete from customer
				where customer_id = ?
				""";

		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, cust_id);

			rstCount = pst.executeUpdate();

		} catch (SQLException e) {
			rstCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, pst, conn);
		}

//		System.out.println("delete결과: " + rstCount);
		return rstCount;
	}

	// CustomerVO의 객체 만드는 함수
	private CustomerVO makeCust(ResultSet rs) throws SQLException {
		CustomerVO cust = new CustomerVO();
		cust.setCustomer_id(rs.getString("customer_id"));
		cust.setCustomer_pw(rs.getString("customer_pw"));
		cust.setCustomer_name(rs.getString("customer_name"));
		cust.setBirth(rs.getInt("birth"));
		cust.setPhone_number(rs.getString("phone_number"));

		return cust;
	}

}
