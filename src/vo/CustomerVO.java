package vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerVO {
	private String customer_id;
	private String customer_pw;
	private String customer_name;
	private int birth;
	private String phone_number;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder	.append("ID :\t\t")
				.append(customer_id)
				.append("\nPW :\t\t")
				.append(customer_pw)
				.append("\n이름 :\t\t")
				.append(customer_name)
				.append("\n생년월일 :\t")
				.append(birth)
				.append("\n휴대전화 :\t")
				.append(phone_number)
				.append("\n");
		return builder.toString();
	}
	
}
