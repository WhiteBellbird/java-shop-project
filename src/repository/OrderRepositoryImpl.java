package repository;

import domain.CartItem;
import domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

public class OrderRepositoryImpl implements OrderRepository{

    // src 폴더 밖에 있는 data 폴더안에 있는 orders.dat에 path 지정
    private final Path DATA_FILE = Paths.get("orderData", "orders.dat");
    // orders.dat를 위한 폴더(/data)가 없다면 생성해준다.
    public OrderRepositoryImpl(){
        try {
            Files.createDirectories(DATA_FILE.getParent());
        } catch (IOException e) {
            System.out.println("데이터 파일을 위한 폴더 생성 불가");
        }
    }
    List<Order> orders = new ArrayList<Order>();

    @Override
    public Order saveOrder(Map<String, CartItem> items) {
    	
        return null;
    }
    @Override
    public Order updateOrder() {
        return null;
    }
    @Override
    public Order getOrder() {
        return null;
    }
}
