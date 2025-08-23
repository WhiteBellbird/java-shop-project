package service;

import domain.*;
import exception.*;
import helper.DiscountRate;
import repository.*;

import java.time.LocalDateTime;
import java.util.*;

public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void cancelOrder(String orderId) {
        try {
            Order order = Optional.of(orderRepository.getOrderByOrderId(orderId)).orElseThrow(() ->
                    new OrderNotFoundException("Order Not Found"));
            order.cancelOrder();
            orderRepository.commit();
        }catch(ShopException e) {
            orderRepository.rollback();
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }

    @Override
    public List<Order> displayOrderList(String userId) {
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public Order createOrder(String userId, String productName, int amount, int quantity, String address) {
        // 👉 commit/rollback은 제거, 순수 로직만
        // (createAllOrders에서 전체 트랜잭션 관리)
        // 예외 발생 시 throw 해서 상위에서 rollback 처리

        // 유저 찾기
        User user = Optional.ofNullable(userRepository.findUserByUserId(userId))
                .orElseThrow(() -> new UserNotfoundException(String.format("userId %s is not found.", userId)));

        // 제품 찾기
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotfoundException(
                        String.format("productName %s is not found.", productName)));

        // 카트 찾기
        Cart userCart = cartRepository.findCartByUserId(user.getUserId())
                .orElseThrow(() -> new ShopException("장바구니를 찾을 수 없습니다."));

        // 카트 아이템 찾기
        CartItem cartItem = userCart.getItems().get(product.getProductId());

        // 결제 금액 확인
        int willPaymentPrice = cartItem.getPaymentPrice(quantity);
        if (willPaymentPrice > amount) {
            throw new InSufficientMoneyException("잔액이 부족합니다.");
        }
        int changeMoney = amount - willPaymentPrice;

        // 포인트 적립
        double earn = willPaymentPrice * DiscountRate.defaultDiscountRate;
        user.accumulatePoint(changeMoney + earn);

        // 카트/상품 수정
        cartItem.subQuantity(quantity);
        product.reduceStock(quantity);
        if (cartItem.getQuantity() == 0) {
            userCart.removeProduct(product.getProductId());
        }

        // 주문 생성
        Order order = Order.craeteOrder(user, cartItem, address, LocalDateTime.now());

        // 저장
        cartRepository.saveCart(userCart);
        productRepository.save(product);
        userRepository.saveUser(user);
        return orderRepository.saveOrder(order);
    }



    @Override
    public void createAllOrders(String userId, int userTotalAmount, String address) {
        try {
            User user = Optional.ofNullable(userRepository.findUserByUserId(userId))
                    .orElseThrow(() -> new UserNotfoundException(
                            String.format("userId %s is not found.", userId)));

            Cart userCart = cartRepository.findCartByUserId(user.getUserId())
                    .orElseThrow(() -> new ShopException("장바구니를 찾을 수 없습니다."));

            int sum = userCart.getItems().values().stream().mapToInt(CartItem::getTotalPrice).sum();
            int payable = sum - (int)Math.round(user.getPoint());
            if (payable > userTotalAmount) {
                throw new InSufficientMoneyException("잔액이 부족합니다.");
            }

            // 장바구니 전체 주문
            userCart.getItems().values().forEach(cartItem -> {
                if (cartItem.getQuantity() > 0) {
                    Product product = cartItem.getProduct();
                    this.createOrder(
                            userId,
                            product.getName(),
                            userTotalAmount, // 전체 금액 중 일부 사용 (실제는 분리 필요)
                            cartItem.getQuantity(),
                            address
                    );
                }
            });

            // 한 번만 commit
            userRepository.commit();
            orderRepository.commit();
            productRepository.commit();
            cartRepository.commit();

        } catch (ShopException e) {
            userRepository.rollback();
            orderRepository.rollback();
            productRepository.rollback();
            cartRepository.rollback();
            throw e;
        }
    }

    @Deprecated
    @Override
    public void createSomeOrders(String userId) {
        //List<T> carts = cartRepository.findCartByUserId(userId);



        //고객 존재 확인 (ID 유효한지 검사
        if(userRepository.findUserByUserId(userId) == null){
            throw new UserNotfoundException("확인되지 않는 유저입니다 : " + userId);
        }

        //Cart에 물건 있는지 확인 //주문생성및저장

        if(cartRepository.findCartByUserId(userId).isEmpty()){
            throw new ShopException("장바구니가 비어있습니다.");
        }

        //주문ID 생성
        String orderId = UUID.randomUUID().toString();
        //데이터 저장

        //Order order = new Order(orderId, userId);
        //orderRepository.saveOrder(order);

        //장바구니 비우기

    }


}
