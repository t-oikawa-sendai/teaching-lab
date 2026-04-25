package exercise;

import java.util.List;

public class Ex24_1 {

	public static void main(String[] args) {
		var fellow = List.of(
				new Robot(),
				new Car(),
				new Computer());
		
		fellow.forEach(m->System.out.println(m.greet()));
		
	}

}
