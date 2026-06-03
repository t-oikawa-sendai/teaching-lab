public class Main { 
  public static void main(String[] args) {
    int isHungry = 1;
    String food = "おにぎり";
    System.out.println("こんにちは");
    if (isHungry== 0) {
      System.out.println("お腹がいっぱいです");
    } else {
      System.out.println("はらぺこです");
    }
    if (isHungry == 1) {
      System.out.println(food + "をいただきます");
    }
  System.out.println("ごちそうさまでした");
  }
}
/* 
  6~10行目は、三項演算子を用いると以下のような記述もできます。
  System.out.println(isHungry == 0 ? "お腹がいっぱいです" : "はらぺこです");
*/