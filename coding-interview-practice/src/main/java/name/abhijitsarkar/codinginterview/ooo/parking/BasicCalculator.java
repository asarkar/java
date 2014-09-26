package name.abhijitsarkar.codinginterview.ooo.parking;

public class BasicCalculator implements Calculator {

	public float getHourlyRate(final Ticket ticket) {
		return 5.0f;
	}

	public float getTaxesAndOtherSurcharges(final Ticket ticket) {
		return 1.0f;
	}
}
