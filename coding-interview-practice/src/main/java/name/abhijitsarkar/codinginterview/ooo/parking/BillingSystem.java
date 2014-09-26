package name.abhijitsarkar.codinginterview.ooo.parking;

public interface BillingSystem {
	public Receipt generateBill(final Ticket ticket);

	public String chargeCreditCard(final float amount, final CreditCard creditCard);
}
