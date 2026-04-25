package exercise;

public class XorHasher implements SimpleHasher {

	@Override
	public int computeHash(String input) {
        int result = 0;
        for (char c : input.toCharArray()) {
            result ^= c;
        }
        return result;
	}

}
