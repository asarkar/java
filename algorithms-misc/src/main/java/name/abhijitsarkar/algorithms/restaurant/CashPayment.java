package name.abhijitsarkar.algorithms.restaurant;

public class CashPayment extends Payment {

	@Override
	public void charge(float billAmount, PaymentManager mgr) {
		mgr.charge(billAmount, this);	
	}
}
