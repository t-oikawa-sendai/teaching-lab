package exercise;

import jp.kwebs.lib.Input;

public class Ex16_1 {
    public static void main(String[] args) {
        
        int total = 0;
        for(int i=0; i<5; i++) {
            int number = Input.getInt("整数");
            total += number;
        }
        System.out.println("合計=" + total);
    }
}
