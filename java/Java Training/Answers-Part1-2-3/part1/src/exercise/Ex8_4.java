package exercise;

import jp.kwebs.lib.Input;

public class Ex8_4 {
    public static void main(String[] args) {
        int a = Input.getInt("a");
        int b = Input.getInt("b");
        int c = Input.getInt("c");
        
        int max = Math.max(Math.max(a, b), c);
        System.out.println("a,b,cの中の最大値＝" + max);
    }
}

