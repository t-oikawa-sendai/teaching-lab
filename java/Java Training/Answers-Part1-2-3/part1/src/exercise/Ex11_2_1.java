package exercise;
import jp.kwebs.lib.Input;

public class Ex11_2_1 {
	public static void main(String[] args) {
		double mile = Input.getDouble("マイル");
		mileToKilo(mile);
	}
	public static void mileToKilo(double mile) {
		System.out.println(mile + "マイルは" + mile * 1.609 + "キロメートル");									
	}

}
