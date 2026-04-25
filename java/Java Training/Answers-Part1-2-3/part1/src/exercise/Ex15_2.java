package exercise;

import jp.kwebs.lib.Input;

public class Ex15_2 {
    public static void main(String[] args) {
        int m = Input.getInt("月");
         
        String season = switch(m) {
            case    12, 1, 2    -> "冬";
            case    3, 4, 5     -> "春";
            case    6, 7, 8     -> "夏";
            default             -> "秋";
        };
        System.out.println(season);
    }
}
