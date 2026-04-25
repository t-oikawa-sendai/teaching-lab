package exercise;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Ex32_1 {

	public static void main(String[] args) {
		System.out.println(LocalDate.of(2025, 3, 1));
		System.out.println(LocalTime.of(23, 5, 45));
		System.out.println(LocalDateTime.of(2025, 3, 1, 23, 5, 45));
	}

}
