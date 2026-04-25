package exercise;
import jp.kwebs.lib.Input;

public class Ex11_2_3 {
	public static void main(String[] args) {

		double weight = Input.getDouble("体重(kg)");
		double height = Input.getDouble("身長(cm)");
		
		double bmiIndex = bmi(weight, height);
		System.out.println(bmiIndex);
	}
	
	public static double bmi(double weight, double height) {
		return weight / Math.pow(height/100, 2);
		
	}
}
