package exception;

public class CustomIllegalArgumentException extends ShopException {
	private static final long serialVersionUID = 1L;
	public CustomIllegalArgumentException(String message) {
		super(message);
	}
}
