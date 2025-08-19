package repository;
import java.util.Optional;

import domain.*;

public interface CartRepository {
	public Cart saveCart(Cart cart);
	
	public Cart updateCart(Cart cart);
	
	public void removeCart(Cart cart);
	
	public Optional<Cart> findCartByUserId(String userId);
	
	public void organizeCartList();
	
	// for test
	public void displayCarts();
}
