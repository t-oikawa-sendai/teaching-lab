package exercise;
// Exercise 30_1の解答
/*
 public enum DrinkType {COFFEE, TEA, JUICE}
 */

// Exercise 30_3の解答
public enum DrinkType {
    COFFEE {
        @Override
        public String getCaffeineLevel() {
            return "High";  // コーヒーはカフェイン多め
        }
    },
    TEA {
        @Override
        public String getCaffeineLevel() {
            return "Medium";  // 紅茶は中程度
        }
    },
    JUICE {
        @Override
        public String getCaffeineLevel() {
            return "None";  // ジュースは基本的にカフェインなし
        }
    };
	public abstract String getCaffeineLevel();
}
