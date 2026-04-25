package exercise;

import java.time.LocalDate;
import java.util.List;

public class Ex32_6 {

	public static void main(String[] args) {
		var dates = List.of(
					LocalDate.of(2021, 3, 20),
					LocalDate.of(2025, 4, 8),
					LocalDate.of(2025, 8, 3),
					LocalDate.of(2024, 11, 13),
					LocalDate.of(2025, 10, 2));
		
		var  referenceDate = LocalDate.of(2025, 7, 31); 
		dates.stream()
			 .filter(d->d.isBefore(referenceDate))
			 .forEach(System.out::println);
	}
}
