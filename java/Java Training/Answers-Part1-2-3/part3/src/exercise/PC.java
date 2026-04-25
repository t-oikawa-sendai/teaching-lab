package exercise;
import java.util.Arrays;
import java.util.List;

public record PC(String name, String type, int price, String maker) implements Comparable<PC> {

	public static List<PC> getList() {
		List<PC> list = Arrays.asList(
				new PC("DELO-200", "DESKTOP", 50000, "DELO"),
				new PC("HQ-110A",  "DESKTOP", 68000, "HQ"),
				new PC("PanaMini", "TABLET",  62000, "Panan"),
				new PC("SeakBook", "LAPTOP",  98000, "HQ"),
				new PC("Panalet",  "LAPTOP",  75000, "Panan"),
				new PC("HQ-Star",  "DESKTOP", 60000, "HQ"),
				new PC("LatteAir", "LAPTOP",  85000, "Latte"),
				new PC("Nectop",   "LAPTOP",  79000, "Nect"),
				new PC("DELOPad",  "TABLET",  48000, "DELO"),
				new PC("DELO-100", "DESKTOP", 48000, "DELO"),
				new PC("ARIBAN",   "TABLET",  12000, "Ariban")

	    );
		return list;
	}
	
	// 製品名の辞書順を自然な並べ替えの順序とする
	@Override
	public int compareTo(PC other) {
		return this.name.compareTo(other.name);
	}
	
}
