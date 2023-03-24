package vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersJoinMenuVO {
	private int order_id;
	private String customer_id;
	private String menu_name;
	private int quantity;
	private int overall;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ 주문 번호 : ")
				.append(order_id)
				.append(", 메뉴명 : ")
				.append(menu_name)
				.append(", 수량 : ")
				.append(quantity)
				.append(", 총 금액 : ")
				.append(overall)
				.append(" ]");
		return builder.toString();
	}
	
	public String toStringForAdmin() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ 주문 번호 : ")
				.append(order_id)
				.append(", 고객 ID : ")
				.append(customer_id)
				.append(", 메뉴명 : ")
				.append(menu_name)
				.append(", 수량 : ")
				.append(quantity)
				.append(", 총 금액 : ")
				.append(overall)
				.append(" ]");
		return builder.toString();
	}
}
