package exercise;
import jp.kwebs.lib.Input;
public class Ex24_2 {

	public static void main(String[] args) {
		String input = Input.getString();
	//	SimpleHasher sh = new SumHasher();
		SimpleHasher sh = new XorHasher();
		
		int hashCode = calculateHash(input, sh);
		System.out.println(input + ": " + hashCode);
	}
	
	public static int calculateHash(String input, SimpleHasher sh) {
		int hashCode = sh.computeHash(input);
		return hashCode;
	}
}
