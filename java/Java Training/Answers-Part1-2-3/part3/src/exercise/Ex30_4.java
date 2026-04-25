package exercise;

import java.util.List;

public class Ex30_4{
    public static void main(String[] args) {

    	var drinkTypes = List.of(DrinkType.COFFEE, DrinkType.TEA, DrinkType.JUICE);
    	
    	drinkTypes.stream()
    			  .forEach(t->System.out.println(t.getCaffeineLevel()));
    	
    }
}

