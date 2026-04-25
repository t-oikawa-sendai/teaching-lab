package exercise22;

import java.util.List;

public class Ex22_3 {

	public static void main(String[] args) {

		var list = List.of(
				new Manager("田中宏", "営業", 500000, 6),
				new Developer("藤田一郎", "開発", 500000, "Java"),
				new Developer("斎藤裕子", "開発", 500000, "Python"));
		
		for(var m : list) {
			m.work();
		}
	}

}
