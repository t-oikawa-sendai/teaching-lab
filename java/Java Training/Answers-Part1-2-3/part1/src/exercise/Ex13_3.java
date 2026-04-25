package exercise;

import jp.kwebs.lib.Input;

public class Ex13_3 {
	public static void main(String[] args) {
		String s = Input.getString();
		String disp = s.equals("OK") || s.equals("ok") ? "おめでとう" : "残念" ;
		System.out.println(disp);
	}

}
