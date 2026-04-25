package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex33_2 {

	public static void main(String[] args) {

		Path p = Path.of("meibo.csv");
		try {
			var list = Files.readAllLines(p);
			list.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
