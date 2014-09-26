package name.abhijitsarkar.codinginterview.ooo.parking;

public class Receipt {

	private final String ticketId;
	private String transactionId;
	private final float hourlyRate;
	private final float taxesAndOtherSurcharges;
	private final float total;
	private final int hoursParked;

	public Receipt(final String ticketId, final float hourlyRate, final float taxesAndOtherSurcharges,
			final float total, final int hoursParked) {
		this.ticketId = ticketId;
		this.hourlyRate = hourlyRate;
		this.taxesAndOtherSurcharges = taxesAndOtherSurcharges;
		this.total = total;
		this.hoursParked = hoursParked;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public float getHourlyRate() {
		return hourlyRate;
	}

	public float getTaxesAndOtherSurcharges() {
		return taxesAndOtherSurcharges;
	}

	public float getTotal() {
		return total;
	}

	public int getHoursParked() {
		return hoursParked;
	}
}
