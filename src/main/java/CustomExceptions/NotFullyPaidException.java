package CustomExceptions;

public class NotFullyPaidException extends Exception {
    private String message;
    private long remaining;


    public NotFullyPaidException(String message,long remaining) {
        super(message);
      //  this.message = message1;
        this.remaining = remaining;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public long getRemaining() {
        return remaining;
    }
}
