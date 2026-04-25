package exercise;

import java.util.List;

record Score (String name, int score){} 

public class Ex16_5 {
	public static void main(String[] args) {
        var scores = List.of(
                new Score("田中", 85),
                new Score("鈴木", 66),
                new Score("斎藤", 82),
                new Score("木村", 57),
                new Score("山下", 77) );
         
        for(Score s : scores) {
            if(s.score()<60) {
                System.out.println("60点未満です");
                break;
            }
            System.out.println(s);
        }
 
    }
}
