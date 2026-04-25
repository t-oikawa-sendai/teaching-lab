package exercise;

public class Ex27_5 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		breads.stream()
			  .map(Bread::country)
			  .distinct()
			  .sorted()
			  .forEach(System.out::println);
	}
}
