package exercise;

import java.util.ArrayList;

public class Ex10_9 {

	public static void main(String[] args) {
        var list = new ArrayList<String>();
        list.add("rabbit");
        list.add("cat");
        list.add("dog");
         
        list.add("bear");                          // ①
        System.out.println(list.get(1));           // ②
        System.out.println(list.contains("cat"));  // ③
        System.out.println(list.remove(0));        // ④
        System.out.println(list.size());           // ⑤	
	}
}
