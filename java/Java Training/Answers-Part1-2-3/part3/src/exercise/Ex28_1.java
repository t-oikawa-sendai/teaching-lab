package exercise;

public class Ex28_1 {

	public static void main(String[] args) {
		
		var data = Sales.getList();
		int sum = data.stream()
			.mapToInt(s->s.quantity() *s.pc().price())
			.sum();
		System.out.printf("総売り上げ = \\%,d%n",sum);
	}

}
