package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex33_5 {

	public static void main(String[] args) {

		try {
			Files.createDirectories(Path.of("meibo/csv"));
			Files.createDirectories(Path.of("meibo/txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
