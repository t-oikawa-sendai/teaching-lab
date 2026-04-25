package exercise;
import jp.kwebs.lib.Input;

public class Ex11_2_2 {
	public static void main(String[] args) {

		double weight = Input.getDouble("体重(kg)");
		double height = Input.getDouble("身長(cm)");
		
		bmi(weight, height);
	}
	
	public static void bmi(double weight, double height) {
		double bmiIndex = weight / Math.pow(height/100, 2);
		System.out.println(bmiIndex);
	}

}
