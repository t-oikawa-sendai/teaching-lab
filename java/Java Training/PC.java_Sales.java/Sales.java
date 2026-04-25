package exercise;

import java.util.List;


/*
 * 売上を表すレコード
 * 演習問題で使用する
 */
public record Sales(
		String name,		// 担当者
		String office,		// 支店
		PC pc,				// パソコンの機種
		int quantity		// 数量
    ){	
	public static List<Sales> getList() {
		
		var list = List.of(
				new Sales("田中","東京", new PC("DELO-200", "DESKTOP", 50000, "DELO"), 2), 
				new Sales("田中","東京", new PC("DELO-100", "DESKTOP", 48000, "DELO"), 3),
				new Sales("田中","東京",new PC("LatteAir", "LAPTOP",  85000, "Latte"), 1),
				new Sales("田中","東京", new PC("Panalet",  "LAPTOP",  75000, "Panan"), 2),
				new Sales("田中","東京", new PC("Ariban",   "TABLET",  12000, "Ariban"), 5),
				new Sales("佐藤","東京", new PC("HQ-110A",  "DESKTOP", 68000, "HQ"), 3),
				new Sales("佐藤","東京", new PC("SeakBook", "LAPTOP",  98000, "HQ"), 1),
				new Sales("佐藤","東京", new PC("PanaMini", "TABLET",  62000, "Panan"), 2),
				new Sales("鈴木","大阪", new PC("DELO-200", "DESKTOP", 50000, "DELO"), 1),
				new Sales("鈴木","大阪", new PC("HQ-Star",  "DESKTOP", 60000, "HQ"), 2),
				new Sales("鈴木","大阪", new PC("Nectop",   "LAPTOP",  79000, "Nect"), 4),
				new Sales("鈴木","大阪", new PC("DELOPad",  "TABLET",  48000, "DELO"), 1),
				new Sales("木村","大阪", new PC("DELO-200", "DESKTOP", 50000, "DELO"), 5),
				new Sales("木村", "大阪",new PC("Nectop",   "LAPTOP",  79000, "Nect"), 2)
				);
		
		return list;
		
	}
}
