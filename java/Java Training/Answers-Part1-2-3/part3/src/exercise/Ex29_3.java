package exercise;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex29_3 {

	public static void main(String[] args) {
		String line = read();
		System.out.println(line);
	}
	public static String read() {
		Path p = Path.of("someText.txt");
		String line = null;
		try (var reader = Files.newBufferedReader(p)) {
			line = reader.readLine();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
}
