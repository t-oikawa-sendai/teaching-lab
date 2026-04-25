package jp.kwebs.bookstore.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jp.kwebs.bookstore.service.BookService;
import jp.kwebs.bookstore.util.Util;

@Configuration
public class DatabaseInitializer {

	@Bean
	CommandLineRunner init(BookService bs) {
		CommandLineRunner clr = s->bs.createAllBooks(Util.getBooks());
		return clr;
	}
}
