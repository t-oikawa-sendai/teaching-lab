package exercise;

public class Ex27_6 {

	public static void main(String[] args) {
		
		var breads = Bread.getBreadList();
		var average = breads.stream()
			  .mapToInt(Bread::price)
			  .average();
		System.out.printf("平均＝%.1f%n" ,average.getAsDouble());
	}

}
