package service;

import domain.Product;
import exception.ProductNotfoundException;

public interface CartService {

	Boolean createCart(String userId);

    Boolean addProduct(String userId, String productId, int quantity);
    
    Boolean updateProductQuantity(String userId, String productId, int newQuantity);
    
    Boolean clearCart(String userId);

    String removeProduct(String userId,String productId) throws ProductNotfoundException;
	
	Integer addProductQuantity(String userId,String productId, Integer quantity);
	
	Integer subProductQuantity(String userId,String productId, Integer quantity);
	
	Integer totalProductsPrice(String userId);

	
	Boolean organizeUsersCartsByTotalPrice();
	
	Boolean organizeUsersCartByUserId();
	
	Boolean organizeUsersCarts(String userId);
}
