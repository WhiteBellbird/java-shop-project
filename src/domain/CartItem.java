package domain;

import java.io.Serializable;

import exception.QuantityException;

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
    
    public void addQuantity(int newValue) {
    	this.quantity = this.quantity + newValue;
    }
    
    public void subQuantity(int newValue) throws QuantityException {
    	if(this.quantity < newValue) {
    		throw new QuantityException(String.format("%d is over existing quantity.",newValue));
    	}
    	this.quantity -= newValue;
    }
    
    @Override
    public String toString() {
        return String.format("%s - 수량: %d개", product.getName(), quantity);
    }

	public int compareTo(CartItem value) {
		if(this.getProduct().getName().charAt(0) < value.getProduct().getName().charAt(0)) return -1;
		if(this.getProduct().getName().charAt(0) > value.getProduct().getName().charAt(0)) return 1;
		return 0;
	}
    
}