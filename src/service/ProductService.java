package service;

import java.util.List;

import domain.Product;
import exception.ProductNotfoundException;

public interface ProductService {

	List<Product> getAllProducts();
	
	Product getProductById(String productId) throws ProductNotfoundException;
	
	Boolean addProduct(Product product);
	
	Boolean updateProduct(Product product) throws ProductNotfoundException;
	
	Boolean deleteProduct(String productId) throws ProductNotfoundException;


	Boolean reduceStockByProductId(String productId, int quantity) throws ProductNotfoundException;
}
