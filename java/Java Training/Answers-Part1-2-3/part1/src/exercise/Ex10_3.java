package exercise;

import java.util.List;

public class Ex10_3 {

	public static void main(String[] args) {
		var products = List.of(
				new Product("MT890", "ステンレスネジ", 280, false),
				new Product("MT810", "タッピングネジ", 160, true),
				new Product("MT900", "スクリューネジ", 330, true));
		
		for(var p : products) {
			System.out.println(p.name() + "\t" + p.price());
		}
	}

}
