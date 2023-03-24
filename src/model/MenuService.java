package model;

import java.util.List;

import vo.MenuVO;

public class MenuService {
	MenuDAO menuDao = new MenuDAO();

	// 메뉴 조회 with 정렬
	public List<MenuVO> selectAllMenuOrderBy(String condition, String colName, String ordeyBy) {
		return menuDao.selectAllMenuOrderBy(condition, colName, ordeyBy);
	}

	// menu_id로 특정 메뉴 조회
	public MenuVO selectByMenuId(int menu_id) {
		return menuDao.selectByMenuId(menu_id);
	}

	// ----------------------------------------------

	// [관리자 모드] 메뉴 추가 (insert)
	public String insertMenu(String menu_name, int price) {
		int rst = menuDao.insertMenu(menu_name, price);
		return rst > 0 ? "메뉴 추가가 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}

	// [관리자 모드] 메뉴 수정 (update)
	public String updateMenu(MenuVO menu) {
		int rst = menuDao.updateMenu(menu);
		return rst > 0 ? "메뉴 수정이 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}

	// [관리자 모드] 메뉴 삭제 (delete)
	public String deleteMenu(int menu_id) {
		int rst = menuDao.deleteMenu(menu_id);
		return rst > 0 ? "메뉴 삭제가 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}

	// [관리자 모드] 입고 처리 (update)
	public String updateStock(int menu_id, int stock) {
		int rst = menuDao.updateStock(menu_id, stock);
		return rst > 0 ? "메뉴 입고가 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}
}
