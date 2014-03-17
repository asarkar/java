package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.util.Map;

public class Check {
	private Map<Food, Float> itemizedBill;
	private float tax;
	private float total;
	private float tip;
	
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
}
