package exercise;

import java.util.stream.Collectors;

public class Ex28_3 {

	public static void main(String[] args) {
		
		var data = Sales.getList();
		String names = data.stream()
			.filter(s->s.office().equals("大阪"))			
			.map(s->s.pc().name())
			.distinct()
			.collect(Collectors.joining(","));
		
		System.out.println("大阪支社の機種リスト = " + names);
	}
}
