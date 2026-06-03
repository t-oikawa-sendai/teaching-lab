
public class calc_demo {
    public static void main(String[] args) {
        double dSum = 0.0;
        for (int i = 0; i < 10; i++) {
            dSum += 0.1;
        }

        float fSum = 0.0f;
        for (int i = 0; i < 10; i++) {
            fSum += 0.1f;
        }

        boolean isDoubleMatch = (dSum == 1.0);
        boolean isFloatMatch = (fSum == 1.0f);

        System.out.println("--------------------------------------------------");
        System.out.println(" double,float 計算デモ");
        System.out.println("--------------------------------------------------");

        System.out.println("[double (64bit)]");
        System.out.println(" println  : " + dSum);
        System.out.printf(" 20 digits: %.20f\n", dSum);
        System.out.println(" Result   : " + isDoubleMatch);

        System.out.println("\n[float (32bit)]");
        System.out.println(" println  : " + fSum);
        System.out.printf(" 10 digits: %.10f\n", fSum);
        System.out.println(" Result   : " + isFloatMatch);

        System.out.println("--------------------------------------------------");
    }
}
