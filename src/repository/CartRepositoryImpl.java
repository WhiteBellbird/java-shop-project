package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import domain.Cart;
import domain.CartItem;
import exception.CustomIllegalArgumentException;
import exception.ProductNotfoundException;
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
	//여기부터 추가
    @Override
    public void clearCartByUserId(String userId) throws ProductNotfoundException {
        Optional<Cart> optionalCart = findCartByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.clearCart();
            System.out.println("장바구니가 비워졌습니다.");
        } else {
            throw new ProductNotfoundException("해당 사용자의 장바구니를 찾을 수 없습니다.");
        }
    }
    
    @Override
    public void updateProductQuantity(String userId, String productId, int newQuantity) throws ProductNotfoundException, CustomIllegalArgumentException {
        Optional<Cart> optionalCart = findCartByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Map<String, CartItem> items = cart.getItems();
            
            if (items.containsKey(productId)) {
                CartItem item = items.get(productId);
                
                if (newQuantity <= 0) {
                    throw new CustomIllegalArgumentException("수량은 0보다 커야 합니다.");
                }

                // CartItem에 수량을 직접 설정하는 메서드가 없으므로, 기존 수량과의 차이를 계산하여 추가/삭제
                int currentQuantity = item.getQuantity();
                int difference = newQuantity - currentQuantity;

                if (difference > 0) {
                    item.addQuantity(difference);
                } else if (difference < 0) {
                    // 수량이 감소하는 경우, 0 이하가 되지 않도록
                    try {
                        item.subQuantity(-difference);
                    } catch (exception.QuantityException e) {
                        throw new CustomIllegalArgumentException("수량 감소 오류: " + e.getMessage());
                    }
                }
            } else {
                throw new ProductNotfoundException("상품 없음" );
            }
        }
    }

	/*
	@Override
	public List<CartItem> organizeCartList() {
		
		
		return;
	}
	*/
	
	@Override
	public HashMap<String, CartItem> organizeUserCart(String userId) {
		Cart cart = carts.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
		
		// 오답 노트
		/*
		Set<Entry<String, CartItem>> entrySet = cart.getItems().entrySet();
		List<Entry<String, CartItem>> entryList = new ArrayList<Map.Entry<String,CartItem>>(entrySet);
		
		Collections.sort(entryList, new Comparator<Map.Entry<String, CartItem>>() {
			public int compare(Map.Entry<String, CartItem> entry1, Map.Entry<String, CartItem> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		LinkedHashMap<String, CartItem> sortedMap = new LinkedHashMap<String, CartItem>();
		for(Map.Entry<String, CartItem> entry : entrySet) {
			sortedMap.put(entry.getKey(), entry.getValue());
			
		}
		*/
		
		Set<Map.Entry<String, CartItem>> entrySet = cart.getItems().entrySet();
		List<Map.Entry<String, CartItem>> entryList = new ArrayList<Map.Entry<String,CartItem>>(entrySet);
		
		
		Collections.sort(entryList, new Comparator<Map.Entry<String, CartItem>>() {
			 public int compare(Map.Entry<String,CartItem> entry1, Map.Entry<String,CartItem> entry2) {
				 return entry1.getValue().compareTo(entry2.getValue());
			 };
		});
		LinkedHashMap <String, CartItem> sortedMap = new LinkedHashMap<>();
		for(/*LinkedHashMap <String, CartItem> sort*/ Map.Entry<String, CartItem> entry : entryList) {
			sortedMap.put(entry.getKey(),entry.getValue());
		}
		
		sortedMap.forEach((u,v) -> System.out.println(v));
		//save the sortedMap to the hashMap inside the list of Carts
		for(int i = 0; i < carts.size(); i++) {
			if(carts.get(i).getUserId().equals(userId)) {
				carts.get(i);
			}
		}
		return sortedMap;
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
	@Override
	public List<CartItem> organizeCartList() {
		// TODO Auto-generated method stub
		return null;
	}
}
