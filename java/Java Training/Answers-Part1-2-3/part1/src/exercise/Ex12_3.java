package exercise;

import java.util.List;

public class Ex12_3 {

	public static void main(String[] args) {
		var list = getProductList();
		for(Product p : list) {
			System.out.println(p);
		}
	}
	public static List<Product> getProductList(){
		
		var list = List.of(
				new Product("MT890", "ステンレスネジ", 280, false),
				new Product("MT810", "タッピングネジ", 160, true),
				new Product("MT900", "スクリューネジ", 330, true));
		return list;
	}
	
}
