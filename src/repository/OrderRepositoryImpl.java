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
    List<Order> orders = new ArrayList<Order>();
    List<Order> tempOrders = new ArrayList<Order>();

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
	@Override
	public Order replaceOrder(Order previousOrder, Order changedOrder) {
	    for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOrderId().equals(previousOrder.getOrderId())) {
				orders.set(i, changedOrder);
			}
	    }
		return changedOrder;
	}
	@Override
	public Order updateOrder(Order order) {
	    for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOrderId().equals(order.getOrderId())) {
				orders.set(i, order); // 기존 객체 교체
				return order;
			}
		}
		orders.add(order); // 없으면 새로 추가
		return order;
	}
    @Override
    public Order saveOrder(Order order) {
    	orders.add(order);
        return order;
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
