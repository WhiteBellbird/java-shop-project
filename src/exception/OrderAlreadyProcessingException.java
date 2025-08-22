package exception;

public class OrderAlreadyProcessingException extends ShopException{
    public OrderAlreadyProcessingException(String message) {
        super(message);
    }
}
