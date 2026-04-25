package exercise;

import java.util.List;

public class Ex10_7 {

	public static void main(String[] args) {
        var list = List.of( 
                new Population("北海道", 5250, -6.8), 
                new Population("東京都", 13921, 7.1),
                new Population("大阪府", 8809, -0.4), 
                new Population("福岡県", 5104, -0.7),
                new Population("沖縄県", 1453, 3.9) );
         
        for(Population p : list) {
            System.out.println(p.prefecture() + "(" + p.rate() + ")");
        }
	}

}
