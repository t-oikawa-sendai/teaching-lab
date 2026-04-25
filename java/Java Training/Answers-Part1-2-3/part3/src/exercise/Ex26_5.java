package exercise;
import java.util.ArrayList;
import java.util.Comparator;

public class Ex26_5 {
	record PhoneNumber (String number, String owner) {}
	
	public static void main(String[] args) {
		var numbers = new ArrayList<PhoneNumber>();
		
		numbers.add(new PhoneNumber("12-123-4567", "Tanaka"));
        numbers.add(new PhoneNumber("34-567-8901", "Suzuki"));
        numbers.add(new PhoneNumber("45-678-9012", "Satho"));
        numbers.add(new PhoneNumber("56-789-0123", "Inoue"));
        numbers.add(new PhoneNumber("78-901-2345", "Nakamura"));
        
        numbers.sort(Comparator.comparing(PhoneNumber::number));
        numbers.forEach(System.out::println);  

        System.out.println("データ件数=" + numbers.size());
        
        numbers.sort(Comparator.comparing(PhoneNumber::owner).reversed());
        numbers.forEach(System.out::println);  
        
	}
}
