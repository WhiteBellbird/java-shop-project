package controller;

import domain.Cart;
import domain.CartItem;
import domain.User;
import exception.InvalidatedInputException;
import exception.ShopException;
import service.CartService;

public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	public Cart createCart(User user) throws ShopException {
		return cartService.createCart(user.getUserId());
	}

	public CartItem addProductByCart(User user, String productId, int quantity) throws ShopException {
		if (productId == null || productId.trim().isEmpty()) {
			throw new InvalidatedInputException("ProductId cannot be empty.");
		}
		if (quantity <= 0) {
			throw new InvalidatedInputException("Quantity must be greater than 0.");
		}
		return cartService.addProductByCart(user.getUserId(), productId, quantity);
	}

	public String removeProductByCart(User user, String productId) throws ShopException {
		if (productId == null || productId.trim().isEmpty()) {
			throw new InvalidatedInputException("ProductId cannot be empty.");
		}
		return cartService.removeProductByCart(user.getUserId(),productId);
	}

	public CartItem addProductQuantityByCart(User user, String productId, Integer quantity) throws ShopException {
		if (productId == null || productId.trim().isEmpty()) {
			throw new InvalidatedInputException("ProductId cannot be empty.");
		}
		if (quantity == null || quantity <= 0) {
			throw new InvalidatedInputException("Quantity must be greater than 0.");
		}
		return cartService.addProductQuantityByCart(user.getUserId(), productId, quantity);
	}

	public CartItem subProductQuantityByCart(User user, String productId, Integer quantity) throws ShopException {
		if (productId == null || productId.trim().isEmpty()) {
			throw new InvalidatedInputException("ProductId cannot be empty.");
		}
		if (quantity == null || quantity <= 0) {
			throw new InvalidatedInputException("Quantity must be greater than 0.");
		}
		return cartService.subProductQuantityByCart(user.getUserId(),productId, quantity);
	}

	public Integer calcTotalPriceInCart(User user) throws ShopException {
		return cartService.getCartItemsTotalProductPrice(user.getUserId());
	}

	public Cart clearCart(User user) throws ShopException {
		return cartService.clearCart(user.getUserId());
	}
}
