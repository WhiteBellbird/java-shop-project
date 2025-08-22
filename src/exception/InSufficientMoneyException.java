package exception;

public class InSufficientMoneyException extends ShopException{
    public InSufficientMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InSufficientMoneyException(String message) {
        super(message);
    }
}
