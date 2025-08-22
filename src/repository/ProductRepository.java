package repository;



import java.util.List;

import java.util.Optional;



import domain.Product;



public interface ProductRepository {



// 모든 상품 목록 조회

List<Product> findAll();



// 상품 ID로 상품 조회

Optional<Product> findById(String productId);



// 상품 정보 저장 (새 상품 등록 또는 기존 상품 수정)

Product save(Product product);



// 상품 이름으로 상품 조회

Optional<Product> findByName(String productName);



// 상품 ID로 상품 삭제

void delete(Product product);


void commit();



void rollback();

}