package exercise;

import jp.kwebs.lib.Input;

public class Ex14_1 {
    public static void main(String[] args) {
        int nin = Input.getInt("人数");
        int gaku = nin * 1000;
        if(nin>=5) {
            gaku *= 0.7;
        }
        System.out.printf("%,d円", gaku);
    }
}
