package domain;

import exception.CustomIllegalArgumentException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product implements Serializable {


    private static final int bestSeller = 50;
    private String productId;    //상품ID
    private String name;    //상품
    private String category;    //카테고리
    private int price;    //가격
    private int stock;    //재고
    private int sellCount;
    private String description;        //상품설명
    private LocalDateTime registrationDate;    //등록일시

    public Product(String productId, String name, String category, int price, int stock, String description,
                   int sellCount, LocalDateTime registrationDate) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.sellCount = sellCount;
        this.description = description;
        this.registrationDate = registrationDate;
    }

    public static Product create(String name, String category, int price, int stock, String description) {
        String productId = UUID.randomUUID().toString();
        return new Product(productId, name, category, price, stock, description, 0, LocalDateTime.now());
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

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }


    public int getSellCount() {
        return sellCount;
    }

    public int getStock() {
        return stock;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
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
            throw new CustomIllegalArgumentException("재고가 부족합니다.");
        }
    }

    public void addSellCount(int sellCount) {
        this.sellCount += sellCount;
    }

    public Boolean isBestSeller() {
        return sellCount >= bestSeller;
    }


    public void updateProduct(String newProductName,
                              String category,
                              int price,
                              String description) {
        this.name = newProductName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================== [상품 정보] ==================\n");
        sb.append(String.format("상품 ID      : %s\n", productId));
        sb.append(String.format("상품명       : %s\n", name));
        sb.append(String.format("카테고리     : %s\n", category));
        sb.append(String.format("가격         : %,d 원\n", price));
        sb.append(String.format("재고         : %d 개\n", stock));
        sb.append(String.format("판매 수      : %d\n", sellCount));
        sb.append(String.format("상품 설명    : %s\n", description));
        sb.append(String.format("등록 일시    : %s\n", registrationDate));
        sb.append("=================================================\n");
        return sb.toString();
    }
}
		
