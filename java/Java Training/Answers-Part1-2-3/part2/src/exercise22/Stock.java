package exercise22;

public final class Stock {
	private final Product product;		// 商品
	private final int number;			// 在庫数
	
	public Stock(Product product, int number) {
		this.product = new Product(product.getProductNumber(), product.getPrice(), product.getName());
		this.number  = number;
	}
	public Product getProduct() {
		return new Product(product.getProductNumber(), product.getPrice(), product.getName());
	}
	public int getNumber() {
		return number;
	}
	// 生成した移譲メソッド
	public int getProductNumber() {
		return product.getProductNumber();
	}
	public int getPrice() {
		return product.getPrice();
	}
	public String getName() {
		return product.getName();
	}

}

