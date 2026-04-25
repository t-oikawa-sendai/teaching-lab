package exercise;

public class Ex10_2 {

	public static void main(String[] args) {
       
		Product p = new Product("MT890", "ステンレスネジ", 280, false);
        
        System.out.println("商品コード = " + p.code());
        System.out.println("商品名     = " + p.name());
        System.out.println("価格       = " + p.price());
        System.out.println("在庫       = " + p.stock());
	}

}
