package exercise;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Ex32_7 {

	public static void main(String[] args) {
        LocalDate birthday = LocalDate.of(1979,1,3);
        LocalDate entryDate = LocalDate.of(2002, 4, 1);
        System.out.println(ChronoUnit.YEARS.between(birthday, LocalDate.now()) + "才");
 
        var p = Period.between(entryDate, LocalDate.now());
        System.out.printf("勤続%d年%dケ月%n",p.getYears(), p.getMonths());   
	}
}
