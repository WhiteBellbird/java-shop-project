package exception;

public class ProductNotfoundException extends ShopException {

    public ProductNotfoundException(String message) {
        super(message);
    }

    public ProductNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
