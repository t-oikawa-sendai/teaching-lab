package exercise;

import java.util.Comparator;

public class Ex27_3 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		breads.stream()
			  .filter(b->b.country().equals("フランス"))
			  .sorted(Comparator.comparing(Bread::price))
			  .map(b->b.name()+" "+b.price())
			  .forEach(System.out::println);
	}

}
