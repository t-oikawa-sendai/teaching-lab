package exercise;

public class Ex14_4 {
	public static void main(String[] args) {
		int a = 0, b = 0, c = 0, d = 0;
		double[] height = { 175.2, 160.0, 153.6, 177.5, 185.7, 172.3, 191.3 };
		for(double h : height) {
			if(h < 160) {
				++a;
			} else if(h < 170) {
				++b;
			} else if(h < 180) {
				++c;
			} else {
				++d;
			}
		}
		System.out.println("A: " + a);
		System.out.println("B: " + b);
		System.out.println("C: " + c);
		System.out.println("D: " + d);
	}
}
