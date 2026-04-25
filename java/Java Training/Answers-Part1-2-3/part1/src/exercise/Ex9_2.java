package exercise;

public class Ex9_2 {
	public static void main(String[] args) {

		String json = """
				{
					"番号":%d
					"氏名":%s
					"得点":%d
				}
				""";

		System.out.println(json.formatted(12345, "田中宏", 90));
	}
}
