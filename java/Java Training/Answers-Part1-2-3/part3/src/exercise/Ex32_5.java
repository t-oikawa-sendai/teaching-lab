package exercise;

import java.time.LocalDate;

public class Ex32_5 {

	public static void main(String[] args) {
		var date = LocalDate.of(2025, 1, 3)
					.plusYears(5)
					.plusMonths(7)
					.plusDays(13);
		
		System.out.println(date);
	}

}
