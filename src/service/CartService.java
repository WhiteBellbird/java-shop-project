package service;

import domain.Cart;
import domain.CartItem;
import exception.ProductNotfoundException;

public interface CartService {

	Cart createCart(String userId);

    CartItem addProductByCart(String userId, String productId, int quantity);
    
//    Boolean updateProductQuantity(String userId, String productId, int newQuantity);
    
    Cart clearCart(String userId);

    String removeProductByCart(String userId, String productId) throws ProductNotfoundException;
	
	CartItem addProductQuantityByCart(String userId, String productId, Integer quantity);
	
	CartItem subProductQuantityByCart(String userId, String productId, Integer quantity);
	
	Integer getCartItemsTotalProductPrice(String userId);

	Boolean organizeUsersCartsByTotalPrice();
	
	Boolean organizeUsersCartByUserId();
	
	Boolean organizeUsersCarts(String userId);
}
