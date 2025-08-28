package repository;

import domain.Cart;
import domain.CartItem;
import domain.Order;
import domain.User;
import exception.ShopException;
import persistence.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class OrderRepositoryImpl implements OrderRepository{
    // src 폴더 밖에 있는 data 폴더안에 있는 orders.dat에 path 지정
	CartRepository cartRepository;
    List<Order> orders;
    List<Order> tempOrders;

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
    public Order saveOrder(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(order.getOrderId())) {
                orders.set(i, order); // 기존 객체 교체
                return order;
            }
        }

        // 기존 객체 없으면 새로 추가
        orders.add(order);
        return order;
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
    public List<Order> getOrder() {
        return orders;
    }
	@Override
	public Order getOrderByOrderId(String orderId) {
		return orders.stream().filter(u -> u.getOrderId().equals(orderId)).findFirst().orElse(null);
	}
	// 왜 리스트가 리턴값인지 한유저의 오더기록을 보려하는건가? *******************************
	@Override
	public List<Order> getOrderByUserId(String userId) {
		return orders.stream().filter(o -> o.getUser().getUserId().equals(userId)).toList();
	}
    @Override
	public void commit() {
		FileManager.writeObject(DATA_FILE, orders);
		tempOrders = deepCopy(orders);
	}

    @Override
    public void clearAll() {
        tempOrders.clear();
        orders.clear();
        FileManager.writeObject(DATA_FILE, orders);
    }

    // rollBack: tmpUsers 상태로 되돌린 후 파일에 덮어쓰기
	@Override
	public void rollback() {
		orders = deepCopy(tempOrders);
		FileManager.writeObject(DATA_FILE, orders);
	}
    private List<Order> deepCopy(List<Order> source) {
        List<Order> copy = new ArrayList<>();
        for (Order o : source) {
            // User와 CartItem도 새로운 객체로 복사
            User userClone = new User(
                    o.getUser().getUserId(),
                    o.getUser().getUsername(),
                    o.getUser().getEmail(),
                    o.getUser().getPassword(),
                    o.getUser().getRank(),
                    o.getUser().getAddress(),
                    o.getUser().getPoint(),
                    o.getUser().getCoupon(),
                    o.getUser().getPhone(),
                    o.getUser().isAdmin(),
                    LocalDateTime.now(),
                    o.getUser().getLoginTime(),   // List는 mutable하므로 새 ArrayList로 복사
                    o.getUser().getLogoutTime()
            );

            CartItem cartItemClone = new CartItem(
                    o.getCartItem().getProduct(),
                    o.getCartItem().getQuantity()
            );

            Order orderClone = new Order(
                    o.getOrderId(),
                    userClone,
                    cartItemClone,
                    o.getAddress(),
                    o.getStatus(),
                    o.getOrderDate()
            );

            copy.add(orderClone);
        }
        return copy;
    }

}
