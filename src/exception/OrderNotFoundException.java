package exception;

public class OrderNotFoundException extends ShopException{
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
