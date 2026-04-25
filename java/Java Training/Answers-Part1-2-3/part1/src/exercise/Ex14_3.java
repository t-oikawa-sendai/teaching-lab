package exercise;

import jp.kwebs.lib.Input;

public class Ex14_3 {
    public static void main(String[] args) {
        int m = Input.getInt("月");
         
        if(m<1 || m>12) {
            System.out.println("月の値が不正です");
        }else if(m==12 || m<3) {
            System.out.println("冬");
        }else if(m<6) {
            System.out.println("春");
        }else if(m<9) {
            System.out.println("夏");
        }else {
            System.out.println("秋");
        }
    }
}

