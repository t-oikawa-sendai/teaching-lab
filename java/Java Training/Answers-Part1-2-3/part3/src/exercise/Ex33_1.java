package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Ex33_1 {

	public static void main(String[] args) {

		var csv = List.of(
	            "ID,Name,Age,Email,Phone",
	            "1,佐藤 太郎,28,taro.sato@example.com,090-1234-5678",
	            "2,鈴木 花子,34,hanako.suzuki@example.com,080-2345-6789",
	            "3,木村 翔太,42,shota.kimura@example.com,070-3456-7890",
	            "4,山田 佳林,25,karin.yamada@example.com,090-4567-8901",
	            "5,小林 春樹,31,haruki.kobayashi@example.com,080-5678-9012");

		Path p = Path.of("meibo.csv");
		try {
			Files.write(p, csv);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
