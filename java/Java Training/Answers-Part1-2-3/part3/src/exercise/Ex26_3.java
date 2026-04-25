package exercise;
import java.util.HashSet;

public class Ex26_3 {
	public static void main(String[] args) {
		var words = new HashSet<String>();
		words.add("abc");
		words.add("adc");
		words.add("aab");
		words.add("adc");
		
		words.forEach(w->System.out.print(w + " "));
	}
}
