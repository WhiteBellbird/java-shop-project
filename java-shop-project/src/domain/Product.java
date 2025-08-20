package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Product implements Serializable{
	

		String productId;	//상품ID
		String name;	//상품
		String category;	//카테고리
		int price;	//가격
		int stock;	//재고
		String description;		//상품설명
		LocalDateTime regisrationDate;	//등록일시
		
		public Product(String productId, String name, String category, int price, int stock, String description,
				LocalDateTime regisrationDate) {
			//super();
			this.productId = productId;
			this.name = name;
			this.category = category;
			this.price = price;
			this.stock = stock;
			this.description = description;
			this.regisrationDate = regisrationDate;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getStock() {
			return stock;
		}

		public void setStock(int stock) {
			this.stock = stock;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDateTime getRegisrationDate() {
			return regisrationDate;
		}

		public void setRegisrationDate(LocalDateTime regisrationDate) {
			this.regisrationDate = regisrationDate;
		}
		// 재고 추가
		public void addStock(int quantity) {
		    if (quantity > 0) {
		        this.stock += quantity;
		    }
		}

		// 재고 감소 (주문 시)
		public void reduceStock(int quantity) {
		    if (quantity > 0 && this.stock >= quantity) {
		        this.stock -= quantity;
		    } else {
		        // 재고 부족 예외 처리
		        throw new IllegalArgumentException("재고가 부족합니다.");
		    }
		}

		@Override
		public String toString() {
		    return String.format("[%s] %s (%s) - %,d원 / 재고: %d개", 
		        productId, name, category, price, stock);
		}
		
		
		
		
}
		
