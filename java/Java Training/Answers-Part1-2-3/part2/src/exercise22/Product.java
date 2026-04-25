package exercise22;

public class Product {
	private int productNumber;		// 商品番号
	private int price;		// 価格
	private String name;	// 商品名
	
	public Product(int productNumber, int price, String name) {
		this.productNumber = productNumber;
		this.price = price;
		this.name = name;
	}
	public int getProductNumber() {
		return productNumber;
	}
	public int getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
}

