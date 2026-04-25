package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex33_4 {

	public static void main(String[] args) {

		var p = Path.of("meibo.txt");
		try (var reader = Files.newBufferedReader(p)) {
			String line;
			while((line=reader.readLine())!=null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
