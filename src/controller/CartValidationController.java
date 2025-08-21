package controller;

import domain.Cart;
import exception.ProductNotfoundException;
import repository.CartRepository;
import repository.ProductRepository;

public class CartValidationController {
	
	private final CartRepository cartRepository= null;
    private final ProductRepository productRepository = null;
    private final String currentUserId = "user1";
	
	private void ClearCart() {
        System.out.println(" 장바구니 비우기 ");
        try {
            cartRepository.clearCartByUserId(currentUserId);
            
       } catch (ProductNotfoundException e) {
            System.out.println("error" + e.getMessage());
        }
	}
	private void ViewCart() {
	        System.out.println("\n--- ➡️ 장바구니 보기 테스트 ---");
	        Cart cart = ();
	        System.out.println(cart);
	}
}
