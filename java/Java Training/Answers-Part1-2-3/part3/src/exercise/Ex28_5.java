package exercise;

import java.util.stream.Collectors;

public class Ex28_5 {

	public static void main(String[] args) {
		
		var data = Sales.getList();
		var map = data.stream()
			.collect(Collectors.groupingBy(Sales::name,
					Collectors.summingInt(s->s.quantity() * s.pc().price())));
			
		System.out.println("担当者別売上高");
		map.forEach((k,v) -> System.out.printf(" %s: \\%,d%n" ,k,v));
	}

}
