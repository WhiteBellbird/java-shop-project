package test;

import java.time.LocalDateTime;

import domain.Cart;
import domain.Product;
import domain.User;
import repository.*;


public class CartRepoTest {
	private static CartRepository cartRepo = new CartRepositoryImpl();
	
	public static void main(String[] args) {
		
		
		User user = new User("001", "kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0,
				null, null, false, LocalDateTime.now(),
				null, null);
		Cart cart = new Cart(user.getUserId());
		
		
		
		cart.addProduct(new Product("003", "plum", "fruit" , 3000, 245, null, LocalDateTime.now()), 5);
		cart.addProduct(new Product("004", "kiwi", "fruit" , 5000, 25, null, LocalDateTime.now()), 3);
		cart.addProduct(new Product("001", "apple", "fruit" , 1000, 265, null, LocalDateTime.now()), 3);
		cart.addProduct(new Product("002", "banana", "fruit" , 500, 365, null, LocalDateTime.now()), 10);
		cartRepo.saveCart(cart);
		
		System.out.println(cart.getItems());
		
		System.out.println(cartRepo.organizeUserCart(user.getUserId()));
		
		
		
		
	}
	
}
