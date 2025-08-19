package service;

import domain.Product;
import exception.ProductNotfoundException;

public interface CartService {

	
	void createCart(String userId);

    void addProduct(String userId, String productId, int quantity);

    String removeProduct(String userId,String productId) throws ProductNotfoundException;
	
	Integer addProductQuantity(String userId,String productId, Integer quantity);
	
	Integer subProductQuantity(String userId,String productId, Integer quantity);
	
	Integer totalProductsPrice(String userId);
}
