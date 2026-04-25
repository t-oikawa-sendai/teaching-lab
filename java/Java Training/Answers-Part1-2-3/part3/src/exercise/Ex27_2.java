package exercise;

public class Ex27_2 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		breads.stream()
			  .filter(b->b.soldout())
			  .map(Bread::name)
			  .forEach(System.out::println);
	}

}
