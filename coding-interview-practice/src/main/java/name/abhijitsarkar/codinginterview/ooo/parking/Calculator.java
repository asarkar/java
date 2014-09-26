package name.abhijitsarkar.codinginterview.ooo.parking;

public interface Calculator {
	public float getHourlyRate(final Ticket ticket);
	
	public float getTaxesAndOtherSurcharges(final Ticket ticket);
}
