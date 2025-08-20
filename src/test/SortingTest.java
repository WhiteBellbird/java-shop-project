package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import repository.*;
import domain.*;
import java.lang.Comparable;

public class SortingTest {
	
	static CartRepository cartRepo = new CartRepositoryImpl();
	
	public static void main(String[] args) {
		int[][] twoDim = { { 1, 2 }, { 3, 7 }, { 8, 9 }, { 4, 2 }, { 5, 3 } };
		Arrays.sort(twoDim, (a1,a2) -> a2[0] - a1[0]);
		
		for(int i = 0; i < twoDim.length; i++) {
			for(int j = 0; j < 2; j++) {
				System.out.println(twoDim[i][j]);
			}
		}
		
		User user = new User("001", "kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0,
				null, null, false, LocalDateTime.now(),
				null, null, false);
		
		Cart cart = new Cart(user.getUserId());
		
		cart.addProduct(new Product("001", "apple", "fruit" , 1000, 265, null, LocalDateTime.now()), 3);
		cart.addProduct(new Product("002", "banana", "fruit" , 500, 365, null, LocalDateTime.now()), 10);
		cart.addProduct(new Product("003", "plum", "fruit" , 3000, 245, null, LocalDateTime.now()), 5);
		cart.addProduct(new Product("004", "kiwi", "fruit" , 5000, 25, null, LocalDateTime.now()), 3);
		cartRepo.saveCart(cart);
		
		
		//Comparator<Product> c = (s1, s2) -> s1.getName().compareTo(s2.getName());
		//Set<Entry<String, CartItem>> entryset = cart.getItems().entrySet();
		//List<Product> mapValue;
		
		//ArrayList<CartItem> mapValue= new ArrayList<>(cart.getItems().values()); 
		//Collections.sort((List<T>) mapValue);
			
				//Comparator<Student> c = (s1, s2) -> s1.firstName.compareTo(s2.firstName);
				//studList.sort(c)
					
		//SortedSet<CartItem> values = new TreeSet<CartItem>(cart.getItems().values());
		//values.forEach(u -> System.out.println(u));
		
		
		 Set<Map.Entry<String, CartItem>> entrySet = cart.getItems().entrySet();
		 List<Map.Entry<String, CartItem>> entryList = new ArrayList<Map.Entry<String,CartItem>>(entrySet);
		 
		 
		 Collections.sort(entryList, new Comparator<Map.Entry<String, CartItem>>() {
			 public int compare(Map.Entry<String,CartItem> entry1, Map.Entry<String,CartItem> entry2) {
				 return entry1.getValue().compareTo(entry2.getValue());
			 };
		});
		
		LinkedHashMap <String, CartItem> sortedMap = new LinkedHashMap<>();
		for(/*LinkedHashMap <String, CartItem> sort*/ Map.Entry<String, CartItem> entry : entryList) {
			sortedMap.put(entry.getKey(),entry.getValue());
		}
		sortedMap.forEach((u,v) -> System.out.println(v));
		
		
	}
}
