package service;

import domain.*;
import repository.*;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderServiceImpl implements OrderService{

    //orderId, Product
    ArrayList<Order> orders;

    @Override
    public void CancelOrder() {
        //주문한 지 12시간 내라면 취소 가능
    }

    @Override
    public void DisplayOrderList(String orderId) {

    }

    @Override
    public void AllOrder(String sessionId) {
        //고객 존재 확인 (세션ID 유효한지 검사)
        //User user = SessionService.();

        //Cart에 물건 있는지 확인
        //주문생성및저장
        //장바구니 비우기

    }

    @Override
    public void IndividualOrder() {
        //Cart 목록 출력(메인에서 구축예정)
        //특정 상품 선택(제외)
        //제외된 상품 Order 리스트에서 제외
        //주문생성및저장
        //주문한 상품만 장바구니에서 비우기
    }
}
