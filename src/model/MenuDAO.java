package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.OracleUtil;
import vo.MenuVO;

public class MenuDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	int rstCount;

	// 메뉴 조회 with 정렬
	public List<MenuVO> selectAllMenuOrderBy(String condition, String colName, String ordeyBy) {
		List<MenuVO> menuList = new ArrayList<>();
		String sql = "select * from menu where stock " + condition + " 0 order by " + colName + " " + ordeyBy;
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				MenuVO menu = makeMenu(rs);
				menuList.add(menu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}

		return menuList;
	}

	// menu_id로 특정 메뉴 조회
	public MenuVO selectByMenuId(int menu_id) {
		MenuVO menu = null;
		String sql = """
				select *
				from menu
				where menu_id = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, menu_id);
			rs = pst.executeQuery();

			if (rs.next()) {
				menu = makeMenu(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}

		return menu;
	}

	// ----------------------------------------------

	// [관리자 모드] 메뉴 추가 (insert)
	public int insertMenu(String menu_name, int price) {
		String sql = """
				insert into menu(menu_id, menu_name, price)
				values(sep_menu_id.nextval, ?, ?)
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, menu_name);
			pst.setInt(2, price);
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

	// [관리자 모드] 메뉴 수정 (update)
	public int updateMenu(MenuVO menu) {
		String sql = """
				update menu
				set menu_name = ?, price = ?
				where menu_id = ?
				""";

		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, menu.getMenu_name());
			pst.setInt(2, menu.getPrice());
			pst.setInt(3, menu.getMenu_id());
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

	// [관리자 모드] 메뉴 삭제 (delete)
	public int deleteMenu(int menu_id) {
		String sql = """
				delete from menu
				where menu_id = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, menu_id);
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

	// [관리자 모드] 입고 처리 (update)
	public int updateStock(int menu_id, int stock) {
		String sql = """
				update menu
				set stock = ?
				where menu_id = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, stock);
			pst.setInt(2, menu_id);
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

	// MenuVO의 객체 만드는 함수
	private MenuVO makeMenu(ResultSet rs) throws SQLException {
		MenuVO menu = new MenuVO();
		menu.setMenu_id(rs.getInt("menu_id"));
		menu.setMenu_name(rs.getString("menu_name"));
		menu.setPrice(rs.getInt("price"));
		menu.setStock(rs.getInt("stock"));
		menu.setSales(rs.getInt("sales"));

		return menu;
	}
}
