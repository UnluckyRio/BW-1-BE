package exceptions;

public class InputMismatchCustomException extends RuntimeException {
    public InputMismatchCustomException(String message) {
        super(message);
    }
}
