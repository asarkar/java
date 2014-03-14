package name.abhijitsarkar.algorithms.restaurant;

import java.util.List;

public class Staff extends Person {
	public Menu bringMenu() {
		return Restaurant.menu;
	}
	
	public void acceptOrder(List<Food> food) {
		Order order = new Order();
		order.setFood(food);

		Kitchen.placeOrder(order);
	}

	public Check bringCheck(int tableId) {
		return BillingSystem.check(tableId);
	}
}
