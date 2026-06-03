/**
 * 【浮動小数点数の計算誤差確認プログラム】
 * 
 * float(32bit) と double(64bit) の精度の違いを確認
  */
public class CalcDemoEX2 { 
    
    // 設定値
    private static final int LOOP_COUNT = 10;
    private static final double TEST_DATA_DOUBLE = 0.1;
    private static final float TEST_DATA_FLOAT = 0.1f;
    private static final double TARGET_VALUE = 1.0;

    public static void main(String[] args) {
        
        double dSum = 0.0;
        for (int i = 0; i < LOOP_COUNT; i++) {
            dSum += TEST_DATA_DOUBLE;
        }

        float fSum = 0.0f;
        for (int i = 0; i < LOOP_COUNT; i++) {
            fSum += TEST_DATA_FLOAT;
        }

        boolean isDoubleMatch = (dSum == TARGET_VALUE);
        boolean isFloatMatch = (fSum == (float) TARGET_VALUE);

        // --- ターミナル出力 (結果の比較) ---
        System.out.println("-------------------------------------------");
        System.out.println(" double vs float 誤差確認テスト"); 
        System.out.println("-------------------------------------------");

        System.out.println("[double (64bit)]");
        System.out.println(" println  : " + dSum);          
        System.out.printf(" 20 digits: %.20f\n", dSum);    
        System.out.println(" Result   : " + isDoubleMatch);

        System.out.println("\n[float (32bit)]");
        System.out.println(" println  : " + fSum);          
        System.out.printf(" 20 digits: %.20f\n", fSum);    
        System.out.println(" Result   : " + isFloatMatch);

        System.out.println("-------------------------------------------");
    }
}