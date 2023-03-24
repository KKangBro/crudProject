package model;

import java.util.Set;

import vo.CustomerVO;

public class CustomerService {
	CustomerDAO customerDao = new CustomerDAO();

	// 모든 customer의 customer_id 조회
	public Set<String> selectAllCustomerId() {
		return customerDao.selectAllCustomerId();
	}

	// customer_id로 특정 customer 조회
	public CustomerVO selectByCustomerId(String cust_id) {
		return customerDao.selectByCustomerId(cust_id);
	}

	// 회원 가입 (insert)
	public String insertCust(CustomerVO cust) {
		int rst = customerDao.insertCust(cust);
		return rst > 0 ? "회원 가입이 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}

	// 회원 정보 수정 (update)
	public String updateCust(CustomerVO cust) {
		int rst = customerDao.updateCust(cust);
		return rst > 0 ? "회원 정보 수정이 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}

	// 회원 탈퇴 (delete)
	public String deleteCust(String custId) {
		int rst = customerDao.deleteCust(custId);
		return rst > 0 ? "회원 탈퇴가 정상적으로 처리되었습니다. Enter.." : "ERROR";
	}
}
