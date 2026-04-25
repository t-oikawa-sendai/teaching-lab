package exercise;

public class Ex28_4 {

	public static void main(String[] args) {
		
		var data = Sales.getList();
		long count = data.stream()
			.filter(s->s.quantity() * s.pc().price() >=100000)
			.count();
		
		System.out.printf("100,000円以上の受注件数 = %d件%n",count);
	}

}
