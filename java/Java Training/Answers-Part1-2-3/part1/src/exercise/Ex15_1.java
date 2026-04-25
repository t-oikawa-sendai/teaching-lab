package exercise;

import jp.kwebs.lib.Input;

public class Ex15_1 {
    public static void main(String[] args) {
        int m = Input.getInt("月");
         
        switch(m) {
            case    12,1,2  ->  System.out.println("冬");
            case    3,4,5   ->  System.out.println("春");
            case    6,7,8   ->  System.out.println("夏");
            default ->  System.out.println("秋");    
        }
    }
}
