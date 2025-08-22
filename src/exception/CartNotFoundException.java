package exception;

public class CartNotFoundException extends ShopException{
    public CartNotFoundException(String message) {
        super(message);
    }
}
