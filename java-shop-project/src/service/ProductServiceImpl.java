package service;

import java.util.List;

import domain.Product;
import exception.ProductNotfoundException;
import exception.ShopException;
import repository.ProductRepository;

public class ProductServiceImpl implements ProductService{
	
	private ProductRepository repository;
	private int nextProductId = 1; // 자동 생성 ID를 위한 변수

	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}
	
	private synchronized String generateNextProductId() {
        return "P" + nextProductId++;
    }
	@Override
	public List<Product> getAllProducts() {
		// 모든 상품 목록을 리포지토리에서 가져와 반환
		return repository.findAll();
	}

	@Override
	public Product getProductById(String productId) throws ProductNotfoundException {
		return repository.findById(productId).orElseThrow(() ->
				new ProductNotfoundException(productId + "에 해당하는 상품이 없습니다."));
	}

	// 상품이 추가될 파라미터로 바꾸시고, 객체 만드신다음에 저장하세요.
	@Override
	public void addProduct(Product product) {
		try {
			// 아래부터 객체를 생성하고 저장해주세요.
			product.setProductId(generateNextProductId());
            repository.save(product);
			repository.commit();
		} catch (ShopException e) {
			repository.rollback();
		}
	}

	@Override
	public void updateProduct(Product product) throws ProductNotfoundException {
		try {
			// 무엇이 업데이트 될지, 파라미터로 바꾸시고 프로덕트를 업데이트 하시고, 리포지토리에 저장해주세요.
			repository.findById(product.getProductId()).orElseThrow(
                    () -> new ProductNotfoundException(product.getProductId() + "에 해당하는 상품이 없습니다."));
            repository.save(product);
			repository.commit();
		} catch (ShopException e) {
			repository.rollback();
		}
	}

	@Override
	public void deleteProduct(String productId) throws ProductNotfoundException {
		try {
			Product willDelete = repository.findById(productId).orElseThrow(
					() -> new ProductNotfoundException(String.format("product not found by Id : %s",
							productId))
			);
			repository.delete(willDelete);
			repository.commit();
		} catch (ShopException e) {
			repository.rollback();
			throw new ProductNotfoundException(e.getMessage());}
		}
		//삭제를 위한 상품이 존재하는지 확인 후 삭제
//		 Product existingProduct = repository.findById(product.getProductId());
//		 if(existingProduct==null) {
//			 throw new ProductNotfoundException("id에 맞는 상품이 없습니다.");
//		 }
//		repository.delete(product.getProductId());

	@Override
	public void reduceStockByProductId(String productId, int quantity) throws ProductNotfoundException {
		try {
			Product product = repository.findById(productId).get();
			product.reduceStock(quantity);
			repository.save(product);
			repository.commit();
		} catch (ProductNotfoundException e) {
			repository.rollback();
			throw new ProductNotfoundException(e.getMessage());
		}
	}

}
