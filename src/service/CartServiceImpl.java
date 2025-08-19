package service;

import domain.Cart;
import domain.Product;
import domain.User;
import repository.CartRepository;
import repository.UserRepository;

public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createCart(String userId) {
        // 커밋과 롤백 예시
        try {
            User user = userRepository.findUserByUserId(userId);
            Cart cart = Cart.createCart(userId);
            Cart saved = cartRepository.saveCart(cart);
            System.out.println("saved = " + saved);
            cartRepository.commit();
        } catch (Exception e) {
            cartRepository.rollback();
        }
    }

    @Override
    public void addProduct(Product product, int quantity) {

    }

    @Override
    public String removeProduct(String productId) {
        return "";
    }

    @Override
    public Integer addProductQuantity(String productId, Integer quantity) {
        return 0;
    }

    @Override
    public Integer subProductQuantity(String productId, Integer quantity) {
        return 0;
    }

    @Override
    public Integer totalProductsPrice(String userId) {
        return 0;
    }
}
