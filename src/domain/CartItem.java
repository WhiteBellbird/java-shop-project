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

    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public int getPaymentPrice(int willPayQuantity) {
        return product.getPrice() * willPayQuantity;
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
    

	public int compareTo(CartItem value) {
		if(this.getProduct().getName().charAt(0) < value.getProduct().getName().charAt(0)) return -1;
		if(this.getProduct().getName().charAt(0) > value.getProduct().getName().charAt(0)) return 1;
		return 0;
	}
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================== [장바구니 상품] ==================\n");
        sb.append(String.format("상품          : %s\n", product.getName()));
        sb.append(String.format("가격          : %,d 원\n", product.getPrice()));
        sb.append(String.format("수량          : %d\n", quantity));
        sb.append(String.format("총 가격       : %,d 원\n", getTotalPrice()));
        sb.append("=====================================================\n");
        return sb.toString();
    }
}