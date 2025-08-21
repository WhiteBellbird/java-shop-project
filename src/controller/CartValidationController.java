package controller;

import domain.Cart;
import exception.ProductNotfoundException;
import repository.CartRepository;
import repository.ProductRepository;
import service.CartService;
import service.SessionService;

public class CartValidationController {
	
	private final CartService cartService;
	private SessionService sessionService;
	
	private void clearCart() {
        System.out.println(" \n장바구니 비우기 ");
        cartService.clearCart(sessionService.getLoggedInUser().getUserId());
	}
}
