package exercise;

import jp.kwebs.lib.Input;

public class Ex11_2_4 {

	public static void main(String[] args) {
		String name = Input.getString("氏名");
		int score = Input.getInt("得点");
		String msg = message(name, score);
		System.out.println(msg);
	}
	public static String message(String name, int score) {
		String message = """
				こんにちは%sさん。
				あなたの得点は%d点でした。
				""";
		return message.formatted(name, score);
	}

}
