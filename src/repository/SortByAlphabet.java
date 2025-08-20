package repository;

import java.util.Comparator;

import domain.Cart;
import domain.CartItem;

public class SortByAlphabet implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		CartItem a = (CartItem) o1;
		CartItem b = (CartItem) o2;
		
		//첫번쨰가 더 작음: ascii 참고
		if(a.getProduct().getName().charAt(0) < b.getProduct().getName().charAt(0)) return -1;
		//두번쨰가 더 작음: ascii 참고
		if(a.getProduct().getName().charAt(0) > b.getProduct().getName().charAt(0)) return 1;
		
		// 둘다 같은 값
		return 0;
	}
	
}
