package repository;

import domain.CartItem;
import domain.Order;
import domain.User;
import exception.ShopException;
import persistence.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        load();
    }
    private void load() {
		List<Order> read = FileManager.readObject(DATA_FILE);
		if (read != null) {
			orders = read;
			tempOrders = deepCopy(read);
		} else {
			orders = new ArrayList<>();
			tempOrders = new ArrayList<>();
		}
	}
    @Override
	public void commit() {
		FileManager.writeObject(DATA_FILE, orders);
		tempOrders = deepCopy(orders);
	}
	// rollBack: tmpUsers 상태로 되돌린 후 파일에 덮어쓰기
	@Override
	public void rollback() {
		orders = deepCopy(tempOrders);
		FileManager.writeObject(DATA_FILE, orders);
	}
	private List<Order> deepCopy(List<Order> source) {
		return new ArrayList<>(source);
	}
	
    List<Order> orders = new ArrayList<Order>();
    List<Order> tempOrders = new ArrayList<Order>();

    
    @Override
    public Order saveOrder(Order order) {
    	if(order == null) {
    		throw new ShopException("오더가 null 존재하지않습니다");
    	}
    	orders.add(order);
        return order;
    }
    
    // unfinished ******
    @Override
	public Order updateOrder() {
		return null;
	}
    
    @Override
    public Order replaceOrder(Order previousOrder, Order changedOrder) {
    	if(!orders.contains(previousOrder)) {
    		throw new ShopException("바꾸려는 유저를 차지 못했습니다.");
    	}
    	for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOrderId().equals(previousOrder.getOrderId())) {
				orders.set(i, changedOrder);
			}
    	}
		return changedOrder;
    }
    @Override
    public List<Order> getOrder() {
        return orders;
    }
	@Override
	public Order getOrderByOrderId(String orderId) {
		return orders.stream().filter(u -> u.getOrderId().equals(orderId)).findFirst().orElse(null);
	}
	
}
