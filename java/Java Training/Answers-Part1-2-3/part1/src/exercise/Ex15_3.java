package exercise;

import jp.kwebs.lib.Input;

public class Ex15_3 {
    public static void main(String[] args) {
        String code = Input.getString("商品コード");
         
        int tanka = switch(code) {
            case    "a100", "b100", "d100"  ->  100;
            case    "a110", "c100"          ->  200;
            case    "b110", "C110"          ->  210;
            case    "b120"                  ->  250;
            default                         ->  0;
        };
         
        if(tanka!=0) {
            int kosuu = Input.getInt("個数");
            System.out.println("合計金額=" + tanka * kosuu);
        }else {
            System.out.println("商品コードが間違っています");
        }
    }
}
