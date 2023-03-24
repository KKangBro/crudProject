package vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuVO {
	private int menu_id;
	private String menu_name;
	private int price;
	private int stock;
	private int sales;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ 메뉴 번호 : ")
				.append(menu_id)
				.append(",\t메뉴명 : ")
				.append(menu_name)
				.append(",\t가격 : ")
				.append(price)
				.append(",\t재고 : ")
				.append(stock)
				.append(",\t판매량 : ")
				.append(sales)
				.append(" ]");
		return builder.toString();
	}
	
}
