package exception;

public class ShopException extends RuntimeException {

	public ShopException(String message, Throwable cause) {
		super(message, cause);
	}
	public ShopException(String message) {
		super(message);
	}
}
