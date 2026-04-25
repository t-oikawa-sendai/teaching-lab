package exercise;

import jp.kwebs.lib.Input;

public class Ex14_2 {
    public static void main(String[] args) {
        int y = Input.getInt("西暦年");
         
        if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
            System.out.println("うるう年です");
        } else {
            System.out.println("うるう年ではありません");
        }
    }
}

