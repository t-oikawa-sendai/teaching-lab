package exercise;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ex32_4 {

	public static void main(String[] args) {
	    var formatter = DateTimeFormatter.ofPattern("ah時m分s秒");
	    System.out.println(LocalTime.now().format(formatter));
	}

}
