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

    Cart cart;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, 
    		ProductRepository productRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}
    @Override
    public void CancelOrder(String orderId) {
    	try {
            Order order = Optional.of(orderRepository.getOrderByOrderId(orderId)).orElseThrow(() ->
                    new OrderNotFoundException("Order Not Found"));
            order.cancelOrder();
            orderRepository.commit();
    	}catch(ShopException e) {
    		orderRepository.rollback();
    	}
    }

    @Override
    public void DisplayOrderList(String userId) {
        orderRepository.getOrder();
    }

    @Override
    public Order createOrder(String userId, String productName, int amount, int quantity, String address) {
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
            // 카트 안에서 카트 아이템 조회
            CartItem cartItem = userCart.getItems().get(product.getProductId());
            // 카트 아이템에서 수량에 따라 지불할 최종 금액 정산
            int willPaymentPriceByCustomer = cartItem.getPaymentPrice(quantity);
            // 거스름돈
            int changeMoney = 0;
            // 지불해야 할 금액보다 크면 정상처리 아니면 반려
            if(willPaymentPriceByCustomer < amount) {
                changeMoney = amount - willPaymentPriceByCustomer;
            } else {
                throw new InSufficientMoneyException("Insufficient money");
            }
            // 포인트 적립
            user.accumulatePoint(changeMoney);
            double earn = willPaymentPriceByCustomer * DiscountRate.defaultDiscountRate;

            Order order = Order.craeteOrder(user, cartItem, address, LocalDateTime.now());

            product.reduceStock(cartItem.getQuantity());
//            cart.removeProduct(product.getProductId());
            cartRepository.saveCart(cart);
            productRepository.save(product);
            return orderRepository.saveOrder(order);
            orderRepository.commit();
            productRepository.commit();
            cartRepository.commit();
         } catch (ShopException e) {
            productRepository.rollback();
            cartRepository.rollback();
            orderRepository.rollback();
        }
    }

    @Override
    public void CreateSomeOrders(String userId) {
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
        cart.clearCart();
    }

    @Override
    public void CreateAllOrders(String userId) {
        //Cart 목록 출력(메인에서 구축예정)
        //특정 상품 선택(제외)
        //제외된 상품 Order 리스트에서 제외
        //주문생성및저장
        //주문한 상품만 장바구니(items)에서 비우기
    }
}
