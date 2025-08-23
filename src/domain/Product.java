package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product implements Serializable{
	

		private String productId;	//상품ID
		private String name;	//상품
		private String category;	//카테고리
		private int price;	//가격
		private int stock;	//재고
		private String description;		//상품설명
		private LocalDateTime regisrationDate;	//등록일시
		
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

	public static Product create(String name, String category, int price, int stock, String description) {
		String productId = UUID.randomUUID().toString();
		return new Product(productId, name, category, price, stock, description, LocalDateTime.now());
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


		public LocalDateTime getRegisrationDate() {
			return regisrationDate;
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

//		@Override
//		public String toString() {
//		    return String.format("[%s] %s (%s) - %,d원 / 재고: %d개",
//		        productId, name, category, price, stock);
//		}
//
//


	@Override
	public String toString() {
		return "Product{" +
				"productId='" + productId + '\'' +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", price=" + price +
				", stock=" + stock +
				", description='" + description + '\'' +
				", regisrationDate=" + regisrationDate +
				'}';
	}
}
		
