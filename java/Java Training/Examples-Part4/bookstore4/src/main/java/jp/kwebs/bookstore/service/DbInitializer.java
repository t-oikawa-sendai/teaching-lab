/*
package jp.kwebs.bookstore.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import jp.kwebs.bookstore.entity.Book;
import jp.kwebs.bookstore.util.Util;

@Service
public class DbInitializer implements CommandLineRunner {

	BookService bs;
	public DbInitializer(BookService bs) {
		this.bs = bs;
	}
	
	@Override
	public void run(String... args) throws Exception {
		bs.createAllBooks(Util.getBooks());
	}

}
*/