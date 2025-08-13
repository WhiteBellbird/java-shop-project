package repository;

import java.util.List;

import domain.Product;

public interface ProductRepository {

	// 모든 상품 목록 조회
    List<Product> findAll();

    // 상품 ID로 상품 조회
    Product findById(String productId);

    // 상품 정보 저장 (새 상품 등록 또는 기존 상품 수정)
    void save(Product product);

	// 상품 ID로 상품 삭제
    void delete(String productId);
    
    // 다음 상품 ID의 시퀀스 번호 가져오기
    int getNextProductId();
    
    // 테스트용 데이터 초기화
    public void resetData();
}
