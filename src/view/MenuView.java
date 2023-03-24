package view;

import java.text.DecimalFormat;
import java.util.List;

import vo.MenuVO;

public class MenuView {
	public static void print(List<MenuVO> menuList) {
		DecimalFormat formatter = new DecimalFormat("###,###");
		System.out.println("---------------------메 뉴 판---------------------");
		for (MenuVO menu : menuList) {
			String price = formatter.format(menu.getPrice());
			String name = menu.getMenu_name();
			for (int i = 0; i < 10 - name.length(); i++) {
				name += "　";
			}

			System.out.printf("%3d. %s\t\t--\t￦ %s\n", menu.getMenu_id(), name, price);
		}
		System.out.println("--------------------------------------------------");
	}

	public static void printForAdmin(List<MenuVO> menuList) {
		System.out.println("------------------------------------------메 뉴 판-------------------------------------------");
		for (MenuVO menu : menuList) {
			System.out.println(menu);
		}
		System.out.println("---------------------------------------------------------------------------------------------");
	}

	public static void print(String message) {
		System.out.println("[알림] " + message);
	}
}
