package by.clevertec.exception;

public class FlowerNotFoundException extends RuntimeException {

    public FlowerNotFoundException() {
    }

    public FlowerNotFoundException(String message) {
        super(message);
    }

    public FlowerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowerNotFoundException(Throwable cause) {
        super(cause);
    }
}
