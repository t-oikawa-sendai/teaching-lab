package exercise;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Ex29_2 {

	public static void main(String[] args) {
		var data = read();
		data.forEach(System.out::println);
	}
	public static List<String> read() {
		Path p = Path.of("someText.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines; 
	}
}
