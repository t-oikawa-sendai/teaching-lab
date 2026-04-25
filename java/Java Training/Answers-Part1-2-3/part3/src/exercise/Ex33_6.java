package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex33_6 {

	public static void main(String[] args) {

		try {
			Files.move(Path.of("meibo.csv"), Path.of("meibo/csv/meibo.csv"));
			Files.move(Path.of("meibo.txt"), Path.of("meibo/txt/meibo.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
