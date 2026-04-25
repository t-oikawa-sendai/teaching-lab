package exercise;

import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;

public class Ex32_3 {

	public static void main(String[] args) {
		var format = DateTimeFormatter.ofPattern("Gy年M月d日eeee");
		var jdate = JapaneseDate.from(LocalDate.now());
		System.out.println(jdate.format(format));
	}
}
