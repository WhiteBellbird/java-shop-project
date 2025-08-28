package service;

import domain.Cart;
import domain.CartItem;
import domain.User;
import exception.ProductNotfoundException;

import java.util.List;

public interface CartService {

	Cart createCart(String userId);

    CartItem addProductByCart(String userId, String productName, int quantity);
    
//    Boolean updateProductQuantity(String userId, String productId, int newQuantity);
    
    Cart clearCart(String userId);

    String removeProductByCart(String userId, String productName) throws ProductNotfoundException;
	
	CartItem addProductQuantityByCart(String userId, String productName, Integer quantity);
	
	CartItem subProductQuantityByCart(String userId, String productName, Integer quantity);
	
	Integer getCartItemsTotalProductPrice(String userId);

    List<CartItem> showCarts(User user);

	Boolean organizeUsersCartsByTotalPrice();
	
	Boolean organizeUsersCartByUserId();
	
	Boolean organizeUsersCarts(String userId);
}
