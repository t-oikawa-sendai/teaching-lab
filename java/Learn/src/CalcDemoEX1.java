/**
 * 【浮動小数点数の計算誤差確認プログラム2】
 * 
 * Decimal と double(64bit) の精度の違いを確認
 */

import java.math.BigDecimal; 

public class CalcDemoEX1 {
    
    // 設定値
    private static final int LOOP_COUNT = 10;
    private static final String INCREMENT_VAL_STR = "0.1";
    private static final String TARGET_VAL_STR = "1.0";

    public static void main(String[] args) {
        
        BigDecimal bdSum = BigDecimal.ZERO;
        BigDecimal bdIncrement = new BigDecimal(INCREMENT_VAL_STR);
        BigDecimal bdTarget = new BigDecimal(TARGET_VAL_STR);

        for (int i = 0; i < LOOP_COUNT; i++) {
               bdSum = bdSum.add(bdIncrement);
        }

        double dSum = 0.0;
        double dIncrement = Double.parseDouble(INCREMENT_VAL_STR);
        for (int i = 0; i < LOOP_COUNT; i++) {
            dSum += dIncrement;
        }

        boolean isBigDecimalMatch = bdSum.compareTo(bdTarget) == 0;
        boolean isDoubleMatch = (dSum == Double.parseDouble(TARGET_VAL_STR));

        // --- ターミナル出力 ---
        System.out.println("-------------------------------------------");
        System.out.println(" BigDecimal vs double 誤差確認テスト");
        System.out.println("-------------------------------------------");

        System.out.println("[BigDecimal (正確)]");
        System.out.println(" println  : " + bdSum);  
        System.out.printf(" 20 digits: %.20f\n", bdSum);    
        System.out.println(" Result   : " + isBigDecimalMatch);

        System.out.println("\n[double (誤差あり)]");
        System.out.println(" println  : " + dSum);          
        System.out.printf(" 20 digits: %.20f\n", dSum);    
        System.out.println(" Result   : " + isDoubleMatch);

        System.out.println("-------------------------------------------");
    }
}