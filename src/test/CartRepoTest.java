package test;

import java.time.LocalDateTime;

import domain.Cart;
import domain.Product;
import domain.User;
import repository.*;


public class CartRepoTest {
	private static CartRepository cartRepo = new CartRepositoryImpl();
	
	public static void main(String[] args) {
		
		
		User user = new User("111", "kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0,
				null, null, false, LocalDateTime.now(),
				null, null);
		User user2 = new User("222", "susan Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0,
				null, null, false, LocalDateTime.now(),
				null, null);
		User user3 = new User("333", "robert Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0,
				null, null, false, LocalDateTime.now(),
				null, null);
		
		Cart cart3 = new Cart(user3.getUserId());
		Cart cart = new Cart(user.getUserId());
		Cart cart2 = new Cart(user2.getUserId());
		

		cart3.addProduct(new Product("003", "plum", "fruit" , 3000, 245, null, LocalDateTime.now()), 5);
		cart3.addProduct(new Product("004", "kiwi", "fruit" , 5000, 25, null, LocalDateTime.now()), 6);
		cart3.addProduct(new Product("001", "apple", "fruit" , 1000, 265, null, LocalDateTime.now()), 3);
		cart3.addProduct(new Product("002", "banana", "fruit" , 500, 365, null, LocalDateTime.now()), 10);
		
		
		cart.addProduct(new Product("003", "plum", "fruit" , 3000, 245, null, LocalDateTime.now()), 5);
		cart.addProduct(new Product("004", "kiwi", "fruit" , 5000, 25, null, LocalDateTime.now()), 3);
		cart.addProduct(new Product("001", "apple", "fruit" , 1000, 265, null, LocalDateTime.now()), 3);
		cart.addProduct(new Product("002", "banana", "fruit" , 500, 365, null, LocalDateTime.now()), 10);
		
		cart2.addProduct(new Product("003", "plum", "fruit" , 3000, 245, null, LocalDateTime.now()), 5);
		cart2.addProduct(new Product("004", "kiwi", "fruit" , 5000, 25, null, LocalDateTime.now()), 1);
		cart2.addProduct(new Product("001", "apple", "fruit" , 1000, 265, null, LocalDateTime.now()), 3);
		cart2.addProduct(new Product("002", "banana", "fruit" , 500, 365, null, LocalDateTime.now()), 10);
		
		
		cartRepo.saveCart(cart3);
		cartRepo.saveCart(cart);
		cartRepo.saveCart(cart2);
		
		
		System.out.println(cart.getItems());
		System.out.println("************************************************");
		System.out.println(cartRepo.organizeUserCart(user.getUserId()));
		System.out.println("************************************************");
		System.out.println(cartRepo.organizeCartListByUserId());
		System.out.println(cartRepo.organizeCartListByTotalPrice());
		
		System.out.println("***************************************************");
		System.out.println(cartRepo.getUsersCart());
		
		
	}
	
}
