package exercise;

import java.util.List;

public class Ex10_5 {

	public static void main(String[] args) {
        var list = List.of(15.1, 8.75, 10.2);
        double total = 0;
         
        for(double x : list) {
            total += x;
        }
        System.out.println("合計=" + total);
        System.out.println(list);
	}

}
