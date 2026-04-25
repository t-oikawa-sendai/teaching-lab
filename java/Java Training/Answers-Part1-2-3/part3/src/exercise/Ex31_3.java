package exercise;
import java.util.List;

public class Ex31_3 {
	public static void main(String[] args) {
		
		var timeList = List.of("12:31","00:00", "24:00", "23:59","12:60");
        String timeRegex = "^([01]\\d|2[0-3]):[0-5]\\d$";

        // ストリームでフィルタリングし、マッチしたものだけ表示
        timeList.stream()
                .filter(time -> time.matches(timeRegex))
                .forEach(System.out::println);

	}
}