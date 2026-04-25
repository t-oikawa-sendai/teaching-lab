package exercise;

public class Ex27_1 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		breads.stream()
			  .filter(b->b.country().equals("日本"))
			  .forEach(System.out::println);
	}

}
