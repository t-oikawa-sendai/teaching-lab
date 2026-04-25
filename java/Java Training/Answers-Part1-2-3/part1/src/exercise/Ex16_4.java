package exercise;

import java.util.List;

public class Ex16_4 {
    public static void main(String[] args) {
        var names = List.of("apple", "Blackberry", "Lime",  "Mango", "Watermelon");
         
        for(String str : names) {
            if(str.length()<6) {
                continue;
            }
            System.out.println(str);
        }
    }
}
