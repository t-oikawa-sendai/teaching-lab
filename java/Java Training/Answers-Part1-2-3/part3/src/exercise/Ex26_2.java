package exercise;
import java.util.HashMap;
public class Ex26_2 {

	public static void main(String[] args) {
		var map = new HashMap<Integer, String>();
		map.put(1, "Tokyo");
		map.put(2, "Osaka");
		map.put(3, "Fukuoka");
		
		map.forEach((k, v)-> System.out.println(k + ": " + v));
	}

}
