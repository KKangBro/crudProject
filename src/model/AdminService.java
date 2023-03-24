package model;

import java.util.Set;

public class AdminService {
	AdminDAO adminDao = new AdminDAO();

	// admin_id로 admin_pw 조회
	public String selectByAdminId(String admin_id) {
		return adminDao.selectByAdminId(admin_id);
	}

	// 모든 admin의 admin_id 조회
	public Set<String> selectAllAdminId() {
		return adminDao.selectAllAdminId();
	}
}
