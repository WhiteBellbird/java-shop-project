package service;

import domain.*;
import exception.*;
import helper.DiscountRate;
import repository.*;

import java.time.LocalDateTime;
import java.util.*;

public class OrderServiceImpl implements OrderService {

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
    public Order createOrder(String userId, String productName, int payment, int quantity /*정말 필요한 변수인가 의문*/, String address) {
        try {
            // 유저 찾는 메서드
            User user = Optional.of(userRepository.findUserByUserId(userId)).orElseThrow(() ->
                    new UserNotfoundException(String.format("useId %s is not found.", userId)));
            // 제품 찾는 메서드
            Product product = productRepository.findByName(productName).orElseThrow(() -> new ProductNotfoundException(
                    String.format("productName %s is not found.", productName)
            ));
            // 카트 찾는 메서드
            Cart userCart = cartRepository.findCartByUserId(user.getUserId()).get();
            // 카트아이템은 저장한 곳이 없음 카트를 통해서 카트아이템 조회해야함********************************************
            CartItem cartItem = userCart.getItems().get(product.getProductId());
            // 카트 안에서 카트 아이템 조회
            //CartItem cartItem = userCart.getItems().get(product.getProductId());
            // 카트 아이템에서 수량에 따라 지불할 최종 금액 정산
            if(quantity != cartItem.getQuantity()) {
            	System.out.println("본래 구매할 상품량과 지금 선택하신 구매량과 다릅니다. - 입력하신 구매량으로 주문합니다.");
            }else {
            	System.out.println("본래 주문하려시던 구매량과 일치합니다 주문 실행합니다...");
            }
            int willPaymentPriceByCustomer = cartItem.getPaymentPrice(cartItem.getQuantity());
            // 거스름돈
            int changeMoney = 0;
            // 지불해야 할 금액보다 크면 정상처리 아니면 반려
            if (willPaymentPriceByCustomer <= payment) {
                changeMoney = payment - willPaymentPriceByCustomer;
            } else {
                throw new InSufficientMoneyException("Insufficient money");
            }
            // 포인트 적립
            double earn = willPaymentPriceByCustomer * DiscountRate.defaultDiscountRate;
            user.accumulatePoint(changeMoney + earn);

            // 카트아이템에서 수량 줄였어.
            cartItem.subQuantity(quantity);
            // 프로덕트에 재고 줄였어
            product.reduceStock(quantity);
            product.addSellCount(quantity);
            //


            // 그 수량이 0이면 카트에서 사라져야지.
            if (cartItem.getQuantity() == 0) {
                userCart.removeProduct(product.getProductId());
            }
            // 주문 생성
            // 여기서 만약 에러를 일부러 발생시킨다면, 실제로 롤백이 되는지 테스트
//            throwError();
            Order order = Order.craeteOrder(user, cartItem, address, LocalDateTime.now());
            // 카트 수정된거 저장
            cartRepository.saveCart(userCart);
            // 제품 수정된거 저장
            productRepository.save(product);
            userRepository.saveUser(user);
            Order saved = orderRepository.saveOrder(order);

            userRepository.commit();
            orderRepository.commit();
            productRepository.commit();
            cartRepository.commit();
            System.out.println("[DEBUG] CreateOrder success : " + user);
            System.out.println("[DEBUG] CreateOrder success : " + saved);
            System.out.println("[DEBUG] CreateOrder success : " + order);
            return saved;
        } catch (ShopException e) {
            userRepository.rollback();
            productRepository.rollback();
            cartRepository.rollback();
            orderRepository.rollback();
            System.out.println("[ERROR] CreateOrder error : " + e.getMessage());
            throw e;
        }
    }


    private void throwError() {
        try {
            throw new ShopException("강제 에러 ");
        } catch (ShopException e) {
            throw  e;
        }
    }

    @Override
    public Order cancelOrder(String orderId) {
        try {
            Order order = Optional.of(orderRepository.getOrderByOrderId(orderId)).orElseThrow(() ->
                    new OrderNotFoundException("Order Not Found"));
            order.cancelOrder();
            Order savedOrder = orderRepository.saveOrder(order);
            orderRepository.commit();
            System.out.println("[DEBUG] CancelOrder success : " + savedOrder);
            return savedOrder;
        } catch (ShopException e) {
            orderRepository.rollback();
            System.out.println("[ERROR] CancelOrder Error className : " + e.getClass().getSimpleName());
            System.out.println("[ERROR] CancelOrder error : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Order> DisplayOrderList(String userId) {
        return orderRepository.getOrderByUserId(userId);
    }
    @Deprecated
    @Override
    public Boolean CreateSomeOrders(String userId) {
        //List<T> carts = cartRepository.findCartByUserId(userId);


        //고객 존재 확인 (ID 유효한지 검사
        if (userRepository.findUserByUserId(userId) == null) {
            throw new UserNotfoundException("확인되지 않는 유저입니다 : " + userId);
        }

        //Cart에 물건 있는지 확인 //주문생성및저장

        if (cartRepository.findCartByUserId(userId).isEmpty()) {
            throw new ShopException("장바구니가 비어있습니다.");
        }

        //주문ID 생성
        String orderId = UUID.randomUUID().toString();
        //데이터 저장

        //Order order = new Order(orderId, userId);
        //orderRepository.saveOrder(order);

        //장바구니 비우기

        /* cart.clearCart(); */
        //cart.clearCart();
        return null;
    }

    @Override
    public List<Order> CreateAllOrders(String userId, int userTotalAmount, String address) {
        //Cart 목록 출력(메인에서 구축예정)
        //특정 상품 선택(제외)
        //제외된 상품 Order 리스트에서 제외
        //주문생성및저장
        //주문한 상품만 장바구니(items)에서 비우기
        try {
            List<Order> orders = new ArrayList<>();
            // 유저 찾는 메서드
            User user = Optional.of(userRepository.findUserByUserId(userId)).orElseThrow(() ->
                    new UserNotfoundException(String.format("useId %s is not found.", userId)));
            // 카트 찾는 메서드
            Cart userCart = cartRepository.findCartByUserId(user.getUserId()).get();
            
            // userCart.getTotalPricae() 도 가능한가 궁금 *****
            int sum = userCart.getItems().values().stream().mapToInt(CartItem::getTotalPrice).sum();
            
            sum -= (int) Math.round(user.getPoint());
            if (sum > userTotalAmount) {
                throw new InSufficientMoneyException("Insufficient money");
            }

            userCart.getItems().values().forEach(cartItem -> {
                int quantity = cartItem.getQuantity();
                cartItem.subQuantity(quantity);
                Product willBeReduce = cartItem.getProduct();
                if (quantity == 0) {
                    willBeReduce.reduceStock(quantity);
                }
                willBeReduce.addSellCount(quantity);
                Order order = Order.craeteOrder(user, cartItem, address, LocalDateTime.now());
                orderRepository.saveOrder(order);
                productRepository.save(willBeReduce);
                orders.add(order);
            });
            cartRepository.saveCart(userCart);
            userRepository.commit();
            orderRepository.commit();
            productRepository.commit();
            cartRepository.commit();
            System.out.println("[DEBUG] CreateAllOrders success : " + orders.size());
            return orders;
        } catch (ShopException e) {
            userRepository.rollback();
            productRepository.rollback();
            cartRepository.rollback();
            orderRepository.rollback();
            System.out.println("[ERROR] CreateAllOrders error : " + e.getMessage());
            throw e;
        }
    }
}
