package repository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import domain.*;

public interface CartRepository {
	Cart saveCart(Cart cart);
	
	Cart updateCart(Cart cart);
	
	void removeCart(Cart cart);
	
	Optional<Cart> findCartByUserId(String userId);
	
	HashMap<String, CartItem> organizeUserCart(String userId);
	
	List<CartItem> organizeCartList();
	// for test
	void displayCarts();

	void commit();

	void rollback();
}
