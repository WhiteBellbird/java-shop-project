package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Cart;

public class CartRepositroyImpl implements CartRepository{

	
    private final Path DATA_FILE = Paths.get("cartData", "carts.dat");
	private List<Cart> carts;
    
    
	public CartRepositroyImpl() {
        try {
            Files.createDirectories(DATA_FILE.getParent());
        } catch (IOException e) {
            System.out.println("데이터 파일을 위한 폴더 생성 불가");
        }
        carts = new ArrayList<Cart>();
	}
	
	@Override
	public Cart saveCart(Cart cart) {
		carts = FileManager.readObject(DATA_FILE);
		carts.add(cart);
		FileManager.writeObject(DATA_FILE, carts);
		return this.findCartByUserId(cart.getUserId()).get();
	}
	
	@Override
	public Cart updateCart(Cart cart) {
		carts = FileManager.readObject(DATA_FILE);
		carts.add(cart);
		FileManager.writeObject(DATA_FILE, carts);
		return this.findCartByUserId(cart.getUserId()).get();
	}
	
	@Override
	public void removeCart(Cart cart) {
		carts = FileManager.readObject(DATA_FILE);
		carts.remove(cart);
		FileManager.writeObject(DATA_FILE, carts);
	}
	
	@Override
	public Optional<Cart> findCartByUserId(String userId) {
		carts = FileManager.readObject(DATA_FILE);
		return carts.stream().filter(cart->cart.getUserId().equals(userId)).findAny();
	}
	
	@Override
	public void organizeCartList() {
		
	}
	@Override
	public void displayCarts() {
		
	}
	
}
