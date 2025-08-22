package service;

import domain.Cart;
import domain.Product;
import domain.User;
import exception.CartNotFoundException;
import exception.ProductNotfoundException;
import exception.ShopException;
import repository.CartRepository;
import repository.ProductRepository;
import repository.UserRepository;

import java.time.LocalDateTime;

public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Boolean createCart(String userId) {
        // 커밋과 롤백 예시
        try {
            User user = userRepository.findUserByUserId(userId);
            Cart cart = Cart.createCart(user.getUserId());
            Cart saved = cartRepository.saveCart(cart);
            System.out.println("saved = " + saved);
            cartRepository.commit();
            return true;
        } catch (ShopException e) {
            userRepository.rollback();
            cartRepository.rollback();
            return false;
        }
    }

    @Override
    public Boolean addProduct(String userId, String productId, int quantity) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new ProductNotfoundException("Product not found By Id : " + productId));
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            cart.addProduct(product, quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            productRepository.commit();
            return true;
        } catch (ShopException e) {
            cartRepository.rollback();
            productRepository.rollback();
            return false;
        }
    }

    @Override
    public String removeProduct(String userId, String productId) throws ProductNotfoundException {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            cart.removeProduct(productId);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            return productId;
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("error Msg is : " + e.getMessage());
            throw new ProductNotfoundException(e.getMessage());
        }
    }

    @Override
    public Integer addProductQuantity(String userId, String productId, Integer quantity) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            cart.addProductQuantity(productId, quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            return cart.getItems().get(productId).getQuantity();
        } catch (ShopException e) {
            cartRepository.rollback();
            throw new ShopException(e.getMessage());
        }
    }

    @Override
    public Integer subProductQuantity(String userId, String productId, Integer quantity) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            cart.subProductQuantity(productId, quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            return cart.getItems().get(productId).getQuantity();
        } catch (ShopException e) {
            cartRepository.rollback();
            throw new ShopException(e.getMessage());
        }
    }

    @Override
    public Integer totalProductsPrice(String userId) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            return cart.getTotalPrice();
        } catch (ShopException e) {
            throw new ShopException(e.getMessage());
        }
    }
     @Override
     public Boolean updateProductQuantity(String userId, String productId, int newQuantity) {
         try {

             cartRepository.findCartByUserId(userId).orElseThrow(() ->
                     new CartNotFoundException("Cart not found By Id : " + userId));

             cartRepository.updateProductQuantity(userId, productId, newQuantity);
             cartRepository.commit();
             return true;
         } catch (ShopException e) {
             cartRepository.rollback();
             return false;
         }
     }

     @Override
     public Boolean clearCart(String userId) {
         try {
//             cartRepository.clearCartByUserId(userId);
             Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                     new CartNotFoundException("Cart not found By Id : " + userId));
             cart.clearCart();
             Cart saved = cartRepository.saveCart(cart);
             cartRepository.commit();
             return true;
         } catch (ShopException e) {
             cartRepository.rollback();
             return false;
         }
     }
	@Override
	public Boolean organizeUsersCartsByTotalPrice() {
		try {
			cartRepository.organizeCartListByTotalPrice();
            cartRepository.commit();
            return true;
		}catch(ShopException e) {
            cartRepository.rollback();
            return false;
		}
	}
	@Override
	public Boolean organizeUsersCartByUserId() {
		try {
			cartRepository.organizeCartListByUserId();
			cartRepository.commit();
            return true;
		}catch(ShopException e) {
            cartRepository.rollback();
            return false;
		}
	}
	@Override
	public Boolean organizeUsersCarts(String userId) {
		try {
			cartRepository.organizeUserCart(userId);
			cartRepository.commit();
            return true;
		}catch(ShopException e) {
            cartRepository.rollback();
            return false;
		}
	}
}
