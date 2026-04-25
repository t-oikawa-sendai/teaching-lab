package exercise;

import java.util.Comparator;

public class Ex27_4 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		breads.stream()
			  .sorted(Comparator.comparing(Bread::price).reversed())
			  .limit(3)
			  .map(b->b.name()+" "+b.price())
			  .forEach(System.out::println);
	}

}
