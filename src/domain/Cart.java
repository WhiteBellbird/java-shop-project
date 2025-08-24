package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


import exception.CustomIllegalArgumentException;
import exception.ProductNotfoundException;
import exception.QuantityException;

import java.util.*;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    // 상품을 담을수 있는 맥심 50개
    private static final int MAX_PRODUCT = 50;
    private String userId;
    // 카트 넣었다 뺐다한 기록
    //private Map<String, Log> history;
    // key: 상품 ID, value: 상품과 수량을 담는 CartItem 객체
    private LinkedHashMap<String, CartItem> items;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new LinkedHashMap<>();
        //this.history = new HashMap<String, Log>();
    }

    public static Cart createCart(String userId) {
        Cart cart = new Cart(userId);
        return cart;
    }

    //product 추가할 상품 객체 AND quantity 추가할 수량
    public CartItem addProduct(Product product, int quantity) throws CustomIllegalArgumentException {
        CartItem existingItem = null;
        if (product == null || quantity <= 0) {
            throw new CustomIllegalArgumentException("상품 또는 수량이 유효하지 않습니다.");
        }
        // 재고 수량 초과 불가 제약사항
        if (product.getStock() < quantity) {
            throw new CustomIllegalArgumentException("재고가 부족합니다. 현재 재고: " + product.getStock() + "개");
        }
        //상품이 카트 아이템에 존재하는지 확인하고 추가하는거
        String productId = product.getProductId();
        if (items.containsKey(productId)) {
            existingItem = items.get(productId);
//            int newTotalQuantity = existingItem.getQuantity() + quantity;
            existingItem.addQuantity(quantity);
            // 재고 초과 여부 재확인
//            if (product.getStock() < newTotalQuantity) {
//                 throw new CustomIllegalArgumentException("재고를 초과할 수 없습니다. 현재 재고: " + product.getStock() + "개");
//            }
//            existingItem.setQuantity(newTotalQuantity);
        } else {
            // 최대 50개 상품만 담을수 있음.
            if (items.size() >= MAX_PRODUCT) {
                throw new IllegalStateException("장바구니에 담을 수 있는 상품 종류는 최대 " + MAX_PRODUCT + "개입니다.");
            }
            existingItem = new CartItem(product, quantity);
            items.put(productId, existingItem);
            Log log = new Log();
            log.added();
            //history.put(productId, log);
        }
        return existingItem;
    }

    /**
     * 장바구니에서 상품을 삭제합니다.
     * productId 삭제할 상품의 ID
     */
    public void removeProduct(String productId) throws ProductNotfoundException {
        if (productId == null || !items.containsKey(productId)) {
            throw new ProductNotfoundException("CartItem is not in cart : " + productId);
        }
        items.remove(productId);
//        Log log = new Log();
//        log.removed();
        //history.put(productId, log);
    }
    public LinkedHashMap<String, CartItem> replaceCartItem(LinkedHashMap<String, CartItem> changedCartItem) {
    	this.items = new LinkedHashMap<String, CartItem>(changedCartItem);
    	return this.items;
    }
    public void addProductQuantity(String productId, int newQuantity) {
        CartItem item = items.get(productId);
        item.addQuantity(newQuantity);
//        if (productId != null && items.containsKey(productId) && newQuantity > 0) {
//            CartItem item = items.get(productId);
//            // 재고 수량 초과 불가 제약사항
//            if (item.getProduct().getStock() < newQuantity) {
//                throw new CustomIllegalArgumentException("재고를 초과할 수 없습니다. 현재 재고: " + item.getProduct().getStock() + "개");
//            }
//            item.setQuantity(newQuantity);
//        } else if (newQuantity <= 0) {
//            // 수량이 0 이하면 상품 삭제
//            removeProduct(productId);
//        } else {
//            throw new CustomIllegalArgumentException("상품 ID 또는 수량이 유효하지 않습니다.");
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

    public LinkedHashMap<String, CartItem> getItems() {
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
        StringBuilder sb = new StringBuilder("USERID: " + this.getUserId() + "\n--- 장바구니 목록 ---\n");
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
        sb.append(String.format("USERID: %s 의 카트정보 끝 \n\n", this.getUserId()));
        return sb.toString();
    }
    
    
    
    
    
    
}