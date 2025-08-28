package service;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import domain.User;
import exception.CartNotFoundException;
import exception.ProductNotfoundException;
import exception.ShopException;
import repository.CartRepository;
import repository.ProductRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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
    public Cart createCart(String userId) {
        try {
            User user = userRepository.findUserByUserId(userId);
            Cart cart = Cart.createCart(user.getUserId());
            Cart saved = cartRepository.saveCart(cart);
            System.out.println("[DEBUG] createCart = " + saved);
            cartRepository.commit();
            return saved;
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] createCart error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CartItem addProductByCart(String userId, String productName, int quantity) {
        try {
            Product product = productRepository.findByName(productName).orElseThrow(() ->
                    new ProductNotfoundException("Product not found By Name : " + productName));
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            CartItem cartItem = cart.addProduct(product, quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            System.out.println("[DEBUG] add CartItem = " + cartItem);
            return cartItem;
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] add Product By Cart = " + productName);
            throw e;
        }
    }

    @Override
    public String removeProductByCart(String userId, String productName) throws ProductNotfoundException {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            Product product = productRepository.findByName(productName).orElseThrow(() ->
                    new ProductNotfoundException("Product not found By Name : " + productName));
            cart.removeProduct(product.getProductId());
            cartRepository.saveCart(cart);
            cartRepository.commit();
            System.out.println("[DEBUG] remove Product By Cart = " + product.getProductId());
            return product.getName();
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] removeProductByCart error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CartItem addProductQuantityByCart(String userId, String productName, Integer quantity) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            Product product = productRepository.findByName(productName).orElseThrow(() ->
                    new ProductNotfoundException("Product not found By Name : " + productName));
            cart.addProductQuantity(product.getProductId(), quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            return cart.getItems().get(product.getProductId());
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] addProductQuantityByCart error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CartItem subProductQuantityByCart(String userId, String productName, Integer quantity) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            Product product = productRepository.findByName(productName).orElseThrow(() ->
                    new ProductNotfoundException("Product not found By Name : " + productName));
            cart.subProductQuantity(product.getProductId(), quantity);
            cartRepository.saveCart(cart);
            cartRepository.commit();
            return cart.getItems().get(product.getProductId());
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] subProductQuantityByCart error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer getCartItemsTotalProductPrice(String userId) {
        try {
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            return cart.getTotalPrice();
        } catch (ShopException e) {
            System.out.println("[ERROR] get total price : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CartItem> showCarts(User user) {
        try {
            Cart cart = cartRepository.findCartByUserId(user.getUserId()).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + user.getUserId()));
            return cart.getItems().values().stream().toList();
        } catch (ShopException e) {
            System.out.println("[ERROR] get total price : " + e.getMessage());
            throw e;
        }
    }
//     @Override
//     public Boolean updateProductQuantity(String userId, String productId, int newQuantity) {
//         try {
//
//             cartRepository.findCartByUserId(userId).orElseThrow(() ->
//                     new CartNotFoundException("Cart not found By Id : " + userId));
//             cartRepository.updateProductQuantity(userId, productId, newQuantity);
//             cartRepository.commit();
//             return true;
//         } catch (ShopException e) {
//             cartRepository.rollback();
//             return false;
//         }
//     }

    @Override
    public Cart clearCart(String userId) {
        try {
//             cartRepository.clearCartByUserId(userId);
            Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() ->
                    new CartNotFoundException("Cart not found By Id : " + userId));
            cart.clearCart();
            Cart saved = cartRepository.saveCart(cart);
            cartRepository.commit();
            return saved;
        } catch (ShopException e) {
            cartRepository.rollback();
            System.out.println("[ERROR] clearCart error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Deprecated
    public Boolean organizeUsersCartsByTotalPrice() {
        try {
            cartRepository.organizeCartListByTotalPrice();
            cartRepository.commit();
            return true;
        } catch (ShopException e) {
            cartRepository.rollback();
            return false;
        }
    }

    @Override
    @Deprecated
    public Boolean organizeUsersCartByUserId() {
        try {
            cartRepository.organizeCartListByUserId();
            cartRepository.commit();
            return true;
        } catch (ShopException e) {
            cartRepository.rollback();
            return false;
        }
    }

    @Override
    @Deprecated
    public Boolean organizeUsersCarts(String userId) {
        try {
            cartRepository.organizeUserCart(userId);
            cartRepository.commit();
            return true;
        } catch (ShopException e) {
            cartRepository.rollback();
            return false;
        }
    }
}
