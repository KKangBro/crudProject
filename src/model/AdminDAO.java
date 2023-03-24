package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import util.OracleUtil;

public class AdminDAO {
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	int rstCount;

	// admin_id로 admin_pw 조회
	public String selectByAdminId(String admin_id) {
		String admin_pw = null;
		String sql = """
				select admin_pw
				from admin
				where admin_id = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, admin_id);
			rs = pst.executeQuery();

			if (rs.next()) {
				admin_pw = rs.getString("admin_pw");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return admin_pw;
	}

	// 모든 admin의 admin_id 조회
	public Set<String> selectAllAdminId() {
		Set<String> set = new HashSet<>();
		String sql = """
				select admin_id
				from admin
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				set.add(rs.getString("admin_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return set;
	}

}
