package exercise;

import jp.kwebs.lib.Input;

public class Ex12_2 {

	public static void main(String[] args) {
		
		var product = getProduct();
		System.out.println(product);
	}
	public static Product getProduct() {
		String code = Input.getString("商品コード");
		String name = Input.getString("商品名");
		int price = Input.getInt("価格");
		boolean missing = Input.getBoolean("在庫");
		
		return new Product(code, name, price, missing);
	}

}
