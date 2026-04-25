package exercise;

public class Product {
	private int number;		// 商品番号
	private int price;		// 価格
	private String name;		// 商品名
	
	public Product(int number, int price, String name) {
		this.number = number;
		this.price = price;
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public int getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
}

