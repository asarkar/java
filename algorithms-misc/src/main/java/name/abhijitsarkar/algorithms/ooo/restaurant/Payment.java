package name.abhijitsarkar.algorithms.ooo.restaurant;

public abstract class Payment {
	private float amount;
	public abstract void charge(float billAmount, PaymentManager mgr);
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}
