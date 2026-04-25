package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex33_7 {

	public static void main(String[] args) {

		var path = Path.of("meibo/");
		try (var stream = Files.walk(path)) {
			stream.filter(Files::isRegularFile)
				  .map(Path::getFileName)
				  .forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
