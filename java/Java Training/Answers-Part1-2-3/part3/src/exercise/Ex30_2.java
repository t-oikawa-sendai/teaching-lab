package exercise;

public class Ex30_2{
    public static void main(String[] args) {
        DrinkType selectedDrink = DrinkType.COFFEE;

		switch(selectedDrink){
			case	COFFEE	->	System.out.println("コーヒー系飲料です");
			case	TEA		->	System.out.println("紅茶系飲料です");
			default			->	System.out.println("ジュール類です");
        }
    }
}

