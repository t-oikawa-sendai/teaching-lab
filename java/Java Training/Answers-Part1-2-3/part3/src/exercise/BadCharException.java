package exercise;

public class BadCharException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadCharException(String message) {
		super(message);
	}
	
}
