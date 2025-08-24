package service;

import java.util.List;
import java.util.stream.Collectors;

import domain.Product;
import exception.ProductNotfoundException;
import exception.ShopException;
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
	public List<Product> getProductsByCategory(String categoryName) {
		return repository.findByCategory(categoryName);
	}

	@Override
	public List<Product> getProductsByPrice() {
		return repository.findByPrice();
	}

	@Override
	public List<Product> getProductsByBestSeller() {
		return repository.findAll().stream().filter(p -> p.isBestSeller()).collect(Collectors.toList());
	}

	@Override
	public Product findProductByRegistrationDate() {
		return repository.findByLastestProduct().orElseThrow(()->new ProductNotfoundException("" +
				"This shop has any Product."));
	}

	@Override
	public Product getProductByProductName(String productName) {
		return repository.findByName(productName).orElseThrow(() ->
				new ProductNotfoundException(productName + "에 해당하는 상품이 없습니다."));
	}

	// 상품이 추가될 파라미터로 바꾸시고, 객체 만드신다음에 저장하세요.
	@Override
	public Product createProduct(String name, String category, int price, int stock, String description) {
		try {
			// 아래부터 객체를 생성하고 저장해주세요.

			Product product = Product.create(name, category, price, stock, description);
			Product saved = repository.save(product);
			System.out.println("[DEBUG] created product : " + product);
			repository.commit();
			return saved;
		} catch (ShopException e) {
			repository.rollback();
			throw e;
		}
	}

	@Override
	public Product addProductStock(String productName, int stock) {
		try {
			Product product = repository.findByName(productName).orElseThrow(() -> new ProductNotfoundException(
					String.format("%s is not found", productName)
			));
			product.addStock(stock);
			Product saved = repository.save(product);
			System.out.println("[DEBUG] add product stock : " + product);
			repository.commit();
			return saved;
		} catch (ShopException e) {
			repository.rollback();
			throw e;
		}
	}

	@Override
	public Product subtractProductStock(String productName, int stock) {
		try {
			Product product = repository.findByName(productName).orElseThrow(() -> new ProductNotfoundException(
					String.format("%s is not found", productName)
			));
			product.reduceStock(stock);
			Product saved = repository.save(product);

			repository.commit();
			System.out.println("[DEBUG] subtracting stock : " + saved);
			return saved;
		} catch (ShopException e){
			repository.rollback();
			throw e;
		}
	}

    @Override
    public Product updateProduct(String originProductName, String newProductName, String category, int price, String description) {
        try {
            Product product = repository.findByName(originProductName).orElseThrow(() -> new ProductNotfoundException(
                    String.format("%s is not found", originProductName)
            ));
            product.updateProduct(newProductName, category, price, description);
            Product saved = repository.save(product);
            repository.commit();
            return saved;
        } catch (ShopException e) {
            repository.rollback();
            throw e;
        }
    }


    @Override
	public Boolean deleteProduct(String productName) throws ProductNotfoundException {
		try {
			Product willDelete = repository.findByName(productName).orElseThrow(
					() -> new ProductNotfoundException(String.format("product not found by name : %s",
							productName))
			);
			repository.delete(willDelete);
			repository.commit();
			System.out.println("[DEBUG] deleted product : " + willDelete);
			return true;
		} catch (ShopException e) {
			repository.rollback();
			throw e;
		}

		//삭제를 위한 상품이 존재하는지 확인 후 삭제
//		 Product existingProduct = repository.findById(product.getProductId());
//		 if(existingProduct==null) {
//			 throw new ProductNotfoundException("id에 맞는 상품이 없습니다.");
//		 }
//		repository.delete(product.getProductId());
	}


}
