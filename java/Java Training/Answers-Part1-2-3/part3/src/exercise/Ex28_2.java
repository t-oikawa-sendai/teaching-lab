package exercise;

public class Ex28_2 {

	public static void main(String[] args) {
		
		var data = Sales.getList();
		int sum = data.stream()
			.filter(s->s.office().equals("東京"))
			.mapToInt(s->s.quantity() *s.pc().price())
			.sum();
		System.out.printf("総売り上げ = \\%,d%n",sum);
	}

}
