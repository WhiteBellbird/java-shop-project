package repository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import domain.*;

public interface CartRepository {
	Cart saveCart(Cart cart);
	
	Cart updateCart(Cart cart);
	
	void removeCart(Cart cart);
	
	void updateProductQuantity(String userId, String productId, int newQuantity);

	void clearCartByUserId(String userId);
	
	Optional<Cart> findCartByUserId(String userId);
	
	HashMap<String, CartItem> organizeUserCart(String userId);
	
	List<Cart> organizeCartListByTotalPrice();
	
	List<Cart> organizeCartListByUserId();
	// for test
	List<Cart> getUsersCart();

	void commit();

	void rollback();
}
