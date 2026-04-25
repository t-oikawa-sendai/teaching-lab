package exercise;
import java.util.List;

public class Ex12_1 {
	public static void main(String[] args) {
		
		var scores = List.of(15, 22, 30, 8, 13);
		int total = sum(scores);
		System.out.println("合計=" + total);
	}

	public static int sum(List<Integer> scores) {
		
		int total=0;
		for(int score : scores) {
			total += score;
		}
		return total;
	}
}
