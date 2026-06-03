
public class InputSample {

    public int parseAge(String ageStr) throws ValidationException {

        if (ageStr == null || ageStr.trim().isEmpty()) {
            throw new ValidationException("年齢を入力してください");
        }

        try {
            int age = Integer.parseInt(ageStr.trim());

            if (age < 0 || age > 150) {
                throw new ValidationException("年齢が不正です");
            }

            return age;

        } catch (NumberFormatException e) {
            throw new ValidationException("年齢は数字で入力してください");
        }
    }
}

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}