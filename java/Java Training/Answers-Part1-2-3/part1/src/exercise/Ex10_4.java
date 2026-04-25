package exercise;

public class Ex10_4 {

	public static void main(String[] args) {
        double[] data = {0.3, 1.05, 2.2};
        double total = 0;
         
        for(double x : data) {
            total += x;
        }
        System.out.printf("合計=%.2f%n", total);
        System.out.printf("平均=%.2f%n" ,total/data.length);
	}

}
