package jp.kwebs.bookstore.util;

import java.time.LocalDate;
import java.util.List;

import jp.kwebs.bookstore.entity.Book;

public class Util {
	
	public static List<Book> getBooks(){
		var list = List.of(
	   	       	new Book(null,"情報倫理",		LocalDate.of(2015,3,10),3500, List.of("印刷本","電子書籍","オーディオブック")),
	           	new Book(null,"テンプル騎士団",	LocalDate.of(2018,9,8), 4800, List.of("印刷本","電子書籍")),
	           	new Book(null,"材料工学", 		LocalDate.of(2024,1,5), 2800, List.of("印刷本")),
	           	new Book(null,"スポーツ統計", 	LocalDate.of(2020,12,4),1500, List.of("印刷本","電子書籍")),
	           	new Book(null,"太平記縁起", 	LocalDate.of(2021,4,12),3400, List.of("印刷本","電子書籍","オーディオブック")),
	           	new Book(null,"データ分析", 	LocalDate.of(2016,7,3),	4200, List.of("印刷本","電子書籍")),
	           	new Book(null,"社会保障政策", 	LocalDate.of(2024,8,13),2500, List.of("印刷本","電子書籍")),
	           	new Book(null,"社会経済史", 	LocalDate.of(2020,4,10),2700, List.of("印刷本","電子書籍")),
	           	new Book(null,"イスラム建国史", LocalDate.of(2021,2,10),5500, List.of("電子書籍")),
	           	new Book(null,"鋳物の化学", 	LocalDate.of(2023,1,10),3400, List.of("印刷本","電子書籍")),
	           	new Book(null,"健康科学",		LocalDate.of(2015,10,8),2300, List.of("印刷本","電子書籍")),
	           	new Book(null,"世界の鉱山",		LocalDate.of(2023,3,10),1300, List.of("印刷本")),
	           	new Book(null,"日本史",			LocalDate.of(2024,5,10),1600, List.of("印刷本")),
	           	new Book(null,"正覚寺",			LocalDate.of(2021,7,10),1300, List.of("印刷本","オーディオブック")),
	           	new Book(null,"粉末冶金科学",	LocalDate.of(2020,12,7),3300, List.of("印刷本","電子書籍"))
	        );
	    	return list;
	}
	
}
