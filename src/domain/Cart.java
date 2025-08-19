package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import exception.QuantityException;

import java.util.*;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    // 상품을 담을수 있는 맥심 50개
    private static final int MAX_PRODUCT = 50;
    private String userId;
    // 카트 넣었다 뺐다한 기록
    private Map<String, Log> history;
    // key: 상품 ID, value: 상품과 수량을 담는 CartItem 객체
    private Map<String, CartItem> items;
    
    public Cart(String userId) {
    	this.userId = userId;
        this.items = new HashMap<>();
        this.history = new HashMap<String, Log>();
    }
    
    //product 추가할 상품 객체 AND quantity 추가할 수량
    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("상품 또는 수량이 유효하지 않습니다.");
        }
        // 재고 수량 초과 불가 제약사항
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. 현재 재고: " + product.getStock() + "개");
        }
        //상품이 카트 아이템에 존재하는지 확인하고 추가하는거
        String productId = product.getProductId();
        if (items.containsKey(productId)) {
            CartItem existingItem = items.get(productId);
//            int newTotalQuantity = existingItem.getQuantity() + quantity;
            existingItem.addQuantity(quantity);
            // 재고 초과 여부 재확인
//            if (product.getStock() < newTotalQuantity) {
//                 throw new IllegalArgumentException("재고를 초과할 수 없습니다. 현재 재고: " + product.getStock() + "개");
//            }
//            existingItem.setQuantity(newTotalQuantity);
        } else {
            // 최대 50개 상품만 담을수 있음.
            if (items.size() >= MAX_PRODUCT) {
                throw new IllegalStateException("장바구니에 담을 수 있는 상품 종류는 최대 " + MAX_PRODUCT + "개입니다.");
            }
            items.put(productId, new CartItem(product, quantity));
            Log log = new Log();
            log.added();
            history.put(productId, log);
        }
    }
    /**
     * 장바구니에서 상품을 삭제합니다.
     *  productId 삭제할 상품의 ID
     */
    public void removeProduct(String productId) {
        if (productId != null && items.containsKey(productId)) {
            items.remove(productId);
            Log log = new Log();
            log.removed();
            history.put(productId, log);
        }
    }
    /**
     * 장바구니 상품의 수량을 변경합니다.
     *  productId 수량을 변경할 상품의 ID
     *  newQuantity 변경할 수량
     */
    public void addProductQuantity(String productId, int newQuantity) {
    	CartItem item = items.get(productId);
    	item.addQuantity(newQuantity);
//        if (productId != null && items.containsKey(productId) && newQuantity > 0) {
//            CartItem item = items.get(productId);
//            // 재고 수량 초과 불가 제약사항
//            if (item.getProduct().getStock() < newQuantity) {
//                throw new IllegalArgumentException("재고를 초과할 수 없습니다. 현재 재고: " + item.getProduct().getStock() + "개");
//            }
//            item.setQuantity(newQuantity);
//        } else if (newQuantity <= 0) {
//            // 수량이 0 이하면 상품 삭제
//            removeProduct(productId);
//        } else {
//            throw new IllegalArgumentException("상품 ID 또는 수량이 유효하지 않습니다.");
//        }
    }
    
    public void subProductQuantity(String productId, int newQuantity) throws QuantityException {
    	CartItem item = items.get(productId);
    	item.subQuantity(newQuantity);
    }
    
    //장바구니의 총 가격
    public int getTotalPrice() {
        int total = 0;
        for (CartItem item : items.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
    
    //장바구니를 비웁니다.
    public void clearCart() {
        this.items.clear();
    }

    public Map<String, CartItem> getItems() {
        return items;
    }
    
    public String getUserId() {
		return userId;
	}
    
    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "--- 장바구니 목록 ---\n장바구니가 비어있습니다.\n--------------------\n총 가격: 0원\n";
        }
        // 장바구니가 비어있지 않으면 , StringBuilder로 목록만들기 stringBuilder는 덧붙이기 기능
        StringBuilder sb = new StringBuilder("--- 장바구니 목록 ---\n");
        for (CartItem item : items.values()) {
            String itemInfo = String.format("%s - 수량: %d개, 가격: %,d원\n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice() * item.getQuantity());
            sb.append(itemInfo);
        }
        // 총 가격 정보를 추가하기
        sb.append("--------------------\n");
        sb.append(String.format("총 가격: %,d원\n", getTotalPrice()));
        return sb.toString();
    }
}