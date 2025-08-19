package service;

import domain.Product;

public interface CartService {

	
	void createCart(String userId);

	void addProduct(Product product, int quantity);
	
	String removeProduct(String productId);
	
	Integer addProductQuantity(String productId, Integer quantity);
	
	Integer subProductQuantity(String productId, Integer quantity);
	
	Integer totalProductsPrice(String userId);
}
