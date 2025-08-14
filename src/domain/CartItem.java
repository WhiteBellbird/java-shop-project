package domain;

import java.io.Serializable;

// 장바구니에 담긴 상품과 수량 관리
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return String.format("%s - 수량: %d개", product.getName(), quantity);
    }
    
}