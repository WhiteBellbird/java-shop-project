package controller;

import domain.Cart;
import domain.CartItem;
import exception.ShopException;
import service.CartService;
import java.util.List;

public class CartController {
	
	private CartService cartService
	
	private SessionService sessionService;

	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	public void 장바구니생성(String userId) throws ShopException {
		cartService.createCart(userId);
	}
	
	public void 상품추가(String userId, String productId, int quantity) throws ShopException {
		cartService.addProduct(userId, productId, quantity);
	}

	public String 상품삭제(String userId, String productId) throws ShopException {
		return cartService.removeProduct(userId, productId);
	}

	public Integer 상품수량증가(String userId, String productId, Integer quantity) throws ShopException {
		return cartService.addProductQuantity(userId, productId, quantity);
	}

	public Integer 상품수량감소(String userId, String productId, Integer quantity) throws ShopException {
		return cartService.subProductQuantity(userId, productId, quantity);
	}
	

	public void 상품수량변경(String userId, String productId, int newQuantity) throws ShopException {
		cartService.updateProductQuantity(userId, productId, newQuantity);
	}

	public Integer 장바구니총가격계산(String userId) throws ShopException {
		return cartService.totalProductsPrice(userId);
	}


	public void 장바구니비우기(String userId) throws ShopException {
		cartService.clearCart(userId);
	}

}
