package repository;
import domain.*;

public interface CartRepository {
	public Cart saveCart(Cart cart);
	
	public Cart updateCart(Cart cart);
	
	public Cart removeCart(Cart cart);
	
	public Cart getCart(Cart cart);
	
	public void organizeCartList();
	
	// for test
	public void displayCarts();
}
