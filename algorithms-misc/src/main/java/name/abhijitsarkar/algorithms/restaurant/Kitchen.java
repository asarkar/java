package name.abhijitsarkar.algorithms.restaurant;

import java.util.Queue;

public class Kitchen {
	private static Queue<Order> orders;
	
	public static void placeOrder(Order o) {
		orders.add(o);
	}
}
