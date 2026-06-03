import java.math.BigDecimal;

/**
 * 【計算誤差とその解決策：Java SE17 対応版】
 * SE17の環境および試験範囲に準拠した記述です。
 */
public class calc_demo_EX {
    public static void main(String[] args) {
        
        double dSum = 0.0;
        for (int i = 0; i < 10; i++) dSum += 0.1;

        float fSum = 0.0f;
        for (int i = 0; i < 10; i++) fSum += 0.1f;

        // BigDecimalによる正確な計算
        BigDecimal bdSum = BigDecimal.ZERO;
        BigDecimal bdUnit = new BigDecimal("0.1");
 
        for (int i = 0; i < 10; i++) {
            bdSum = bdSum.add(bdUnit);
        }

        // --- 2. 比較判定 ---
        boolean isDoubleMatch = (dSum == 1.0);
        boolean isFloatMatch = (fSum == 1.0f);
        boolean isBigDecimalMatch = (bdSum.compareTo(new BigDecimal("1.0")) == 0);

        // --- 3. ターミナル出力 ---
        System.out.println("--------------------------------------------------");
        System.out.println(" double,float & decimal 計算デモ");
        System.out.println("--------------------------------------------------");

        System.out.println("[double (64bit)]");
        System.out.println(" println  : " + dSum);
        System.out.printf(" 20 digits: %.20f\n", dSum);
        System.out.println(" Result   : " + isDoubleMatch);

        System.out.println("\n[float (32bit)]");
        System.out.println(" println  : " + fSum);
        System.out.printf(" 10 digits: %.10f\n", fSum);
        System.out.println(" Result   : " + isFloatMatch);

        System.out.println("\n[BigDecimal (Object)]");
        System.out.println(" println  : " + bdSum);
        System.out.printf(" 20 digits: %.20f\n", bdSum.doubleValue());
        System.out.println(" Result   : " + isBigDecimalMatch);

        System.out.println("--------------------------------------------------");
    }
}