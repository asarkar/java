package name.abhijitsarkar.algorithms.restaurant;

import java.util.HashMap;
import java.util.Map;

public class BillingSystem {
	private static PaymentManager paymentMgr;

	public static Check check(int tableId) {
		Order order = lookUpOrder(tableId);

		Map<Food, Float> itemizedBill = new HashMap<>();
		float total = 0.0f;
		float price = 0.0f;

		for (Food food : order.getFood()) {
			price = Restaurant.menu.getPrice(food);
			itemizedBill.put(food, price);
			total += price;
		}

		Check check = new Check();
		check.setTotal(total);
		// Set various other check items

		return check;
	}

	private static Order lookUpOrder(int tableId) {
		return null;
	}

	public static float acceptPayment(Check check, Payment payment) {
		payment.charge(check.getTotal(), paymentMgr);

		return payment.getAmount() - check.getTotal();
	}
}
