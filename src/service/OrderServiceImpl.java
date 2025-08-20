package service;

import domain.*;
import exception.ShopException;
import exception.UserNotfoundException;
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
    		//주문한 지 12시간 내라면 취소 가능
            LocalDateTime deadLine = orderRepository.getOrderByOrderId(orderId).getOrderDate().plusHours(12);
            LocalDateTime now = LocalDateTime.now();
            if(!now.isBefore(deadLine)){
                throw new ShopException("주문은 12시간내에만 취소가능합니다");
            }else{
            	orderRepository.getOrderByOrderId(orderId).setStatus("cancel");
            }
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
