package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Product implements Serializable{
	

		private final String productId;	//상품ID
		private String name;	//상품
		private String category;	//카테고리
		private int price;	//가격
		private int stock;	//재고
		private String description;		//상품설명
		private final LocalDateTime registrationDate;	//등록일시
		
		
		public Product(String productId, String name, String category, int price, int stock, String description,
				LocalDateTime registrationDate) {
			//super();
			this.productId = productId;
			this.name = name;
			this.category = category;
			this.price = price;
			this.stock = stock;
			this.description = description;
			this.registrationDate = registrationDate;
		}
		// 상품 등록 시 사용되는 생성자 (ID, 날짜 자동 생성)
	    public Product(String name, String category, int price, int stock, String description) {
	        this.productId = "P" + LocalDateTime.now().toString(); // 예시로 ID를 자동으로 생성
	        this.name = name;
	        this.category = category;
	        this.price = price;
	        this.stock = stock;
	        this.description = description;
	        this.registrationDate = LocalDateTime.now();
	    }
	    
		public String getProductId() {
			return productId;
		}
		public String getName() {
			return name;
		}
		public String getCategory() {
			return category;
		}
		public int getPrice() {
			return price;
		}
		public int getStock() {
			return stock;
		}
		public String getDescription() {
			return description;
		}
		public LocalDateTime getRegistrationDate() {
			return registrationDate;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		//재고 추가
		 public void addStock(int quantity) {
		        if (quantity > 0) {
		            this.stock += quantity;
		        }
		    }
		//재고 감소
		 public void reduceStock(int quantity) {
		        if (quantity > 0 && this.stock >= quantity) {
		            this.stock -= quantity;
		        } else {
		            throw new IllegalArgumentException("재고가 부족합니다.");
		        }
		    }
		 
		 @Override
		 public String toString() {
			return String.format("[%s] %s (%s) - %,d원 / 재고: %d개", 
		            productId, name, category, price, stock);
		 }
		 
		 
}
		
