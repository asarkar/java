package name.abhijitsarkar.codinginterview.ooo.parking;

import java.time.YearMonth;

public class CreditCard {
	final long cardNUmber;
	final YearMonth expiryDate;
	final int securityCode;
	// This may be broken up in which case we'd make a separate class
	final String cardHolderfullName;
	// This may be broken up in which case we'd make a separate class
	final String cardHolderAddress;

	public CreditCard(final long cardNUmber, final YearMonth expiryDate, final int securityCode,
			final String cardHolderfullName, final String cardHolderAddress) {
		this.cardNUmber = cardNUmber;
		this.expiryDate = expiryDate;
		this.securityCode = securityCode;
		this.cardHolderfullName = cardHolderfullName;
		this.cardHolderAddress = cardHolderAddress;
	}
}
