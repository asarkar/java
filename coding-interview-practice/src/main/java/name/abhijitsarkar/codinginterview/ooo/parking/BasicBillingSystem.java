package name.abhijitsarkar.codinginterview.ooo.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class BasicBillingSystem implements BillingSystem {
	private Calculator calculator;

	public BasicBillingSystem(Calculator calculator) {
		this.calculator = calculator;
	}

	public Receipt generateBill(final Ticket ticket) {
		final float hourlyRate = calculator.getHourlyRate(ticket);
		final float taxesAndOtherSurcharges = calculator.getTaxesAndOtherSurcharges(ticket);

		long hours = Duration.between(LocalDateTime.now(), ticket.getEntryTimestamp()).toHours();

		hours = Math.max(1, hours);

		final float total = hours * hourlyRate + taxesAndOtherSurcharges;

		return new Receipt(ticket.getId(), hourlyRate, taxesAndOtherSurcharges, total, (int) hours);
	}

	public String chargeCreditCard(final float amount, final CreditCard creditCard) {
		return UUID.randomUUID().toString();
	}

	public Calculator getCalculator() {
		return calculator;
	}

	public void setCalculator(Calculator calculator) {
		this.calculator = calculator;
	}
}
