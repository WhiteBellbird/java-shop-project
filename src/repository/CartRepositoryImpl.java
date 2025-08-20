package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Cart;
import persistence.FileManager;

public class CartRepositoryImpl implements CartRepository {

	private final Path DATA_FILE = Paths.get("cartData", "carts.dat");
	private List<Cart> carts;
	private List<Cart> tmpCarts;

	public CartRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
		carts = new ArrayList<>();
		tmpCarts = new ArrayList<>();
		load(); // 초기 데이터 로딩
	}
	private void load() {
		List<Cart> read = FileManager.readObject(DATA_FILE);
		if (read != null) {
			carts = read;
			tmpCarts = deepCopy(read);
		} else {
			carts = new ArrayList<>();
			tmpCarts = new ArrayList<>();
		}
	}
	@Override
	public Cart saveCart(Cart cart) {
		carts.add(cart);
		return cart;
	}
	@Override
	public Cart updateCart(Cart cart) {
		for (int i = 0; i < carts.size(); i++) {
			if (carts.get(i).getUserId().equals(cart.getUserId())) {
				carts.set(i, cart); // 기존 객체 교체
				return cart;
			}
		}
		carts.add(cart); // 없으면 새로 추가
		return cart;
	}
	@Override
	public void removeCart(Cart cart) {
		carts.removeIf(c -> c.getUserId().equals(cart.getUserId()));
	}
	@Override
	public Optional<Cart> findCartByUserId(String userId) {
		return carts.stream().filter(c -> c.getUserId().equals(userId)).findAny();
	}
	@Override
	public void organizeCartList() {
		// 필요 시 정렬/정리 로직 구현
		
	}
	@Override
	public void displayCarts() {
	}
	@Override
	public void commit() {
		FileManager.writeObject(DATA_FILE, carts);
		tmpCarts = deepCopy(carts); // commit 후 tmpCarts 갱신
	}
	@Override
	public void rollback() {
		carts = deepCopy(tmpCarts); // 깊은 복사로 이전 상태 복원
		FileManager.writeObject(DATA_FILE, carts);
	}
	private List<Cart> deepCopy(List<Cart> source) {
        List<Cart> copy = new ArrayList<>(source);
		return copy;
	}
}
