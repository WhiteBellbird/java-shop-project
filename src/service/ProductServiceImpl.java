package service;

import java.util.List;

import domain.Product;
import exception.ProductNotfoundException;
import repository.ProductRepository;

public class ProductServiceImpl implements ProductService{
	
	private ProductRepository repository;

	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}
	@Override
	public List<Product> getAllProducts() {
		// 모든 상품 목록을 리포지토리에서 가져와 반환
		return repository.findAll();
	}

	@Override
	public Product getProductById(String productId) throws ProductNotfoundException {
		Product product = repository.findById(productId);
		//상품이 null일 경우 예외를 던짐
		if(product==null) {
			throw new ProductNotfoundException(productId + "에 해당하는 상품이 없습니다.");
		}
		return product;
	}

	@Override
	public void addProduct(Product product) {
		//상품ID가 null이거나 비어있을 경우 자동생성
		if(product.getProductId()==null || product.getProductId().isEmpty()) {
			int nextId = repository.getNextProductId() + 1;
			product.setProductId("P"+ nextId);
 		}
		repository.save(product);
	}

	@Override
	public void updateProduct(Product product) throws ProductNotfoundException {
		//수정을 위한 상품이 존재하는지 확인 후 수정
		Product existingProduct = repository.findById(product.getProductId());
		if(existingProduct==null) {
			throw new ProductNotfoundException("id에 맞는 상품이 없습니다.");
		}
		repository.save(product);
	}

	@Override
	public void deleteProduct(Product product) throws ProductNotfoundException {
		//삭제를 위한 상품이 존재하는지 확인 후 삭제
		 Product existingProduct = repository.findById(product.getProductId());
		 if(existingProduct==null) {
			 throw new ProductNotfoundException("id에 맞는 상품이 없습니다.");
		 }
		repository.delete(product.getProductId());
	}

}
