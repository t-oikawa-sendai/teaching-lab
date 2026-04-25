package exercise;

public class SumHasher implements SimpleHasher {

	@Override
	public int computeHash(String input) {
        int total = 0;
        for (char c : input.toCharArray()) {
            total += c;
        }
        return total;
	}

}
