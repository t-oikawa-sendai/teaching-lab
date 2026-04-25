package exercise;
import java.util.List;
public record Bread(String country, String name, int price, boolean soldout) {

	public static List<Bread> getBreadList() {
		return List.of(
			new Bread("日本", "カレーパン", 260, false),	
			new Bread("フランス", "クロワッサン", 230, false),	
			new Bread("イタリア", "フォカッチャ", 250, true),	
			new Bread("アメリカ", "ベーグル", 180, false),	
			new Bread("ドイツ", "ライムギパン", 300, false),	
			new Bread("イギリス", "スコーン", 180, false),	
			new Bread("フランス", "バケット", 380, false),	
			new Bread("イギリス", "マフィン", 220, true),	
			new Bread("アメリカ", "食パン", 250, false),	
			new Bread("フランス", "カンパーニュ", 350, false),	
			new Bread("ドイツ", "プレッツェル", 310, false),	
			new Bread("アメリカ", "ロールパン", 120, false),	
			new Bread("日本", "アンパン", 180, false));	
	}
}