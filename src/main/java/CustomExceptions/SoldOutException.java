package CustomExceptions;

public class SoldOutException extends Exception {

    private String message;

    public SoldOutException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
