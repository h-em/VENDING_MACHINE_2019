package CustomExceptions;

public class NotSufficientChangeException extends Exception {


    private String message;

    public NotSufficientChangeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
