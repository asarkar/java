package name.abhijitsarkar.refactoring.chapter1;

import java.util.ArrayList;

public class Rentals extends ArrayList<AbstractRental> {

	private static final long serialVersionUID = 3844030133134385612L;
	private String customerName;

	/*
	 * These Constructors are recommended by the Collections API but in this
	 * case we are making an exception because we want customerName to be a
	 * mandatory immutable argument
	 */
	// public Rentals() {
	// }
	//
	// public Rentals(Collection<AbstractRental> rentals) {
	// this.addAll(rentals);
	// }

	public Rentals(String customerName) {
		this.customerName = customerName;
	}

	public String rentalStatement() {
		StringBuilder statement = new StringBuilder();
		statement.append("Rental Record for ").append(customerName)
				.append("\n");

		double totalAmount = 0.0d;
		double thisAmount = 0.0d;
		int frequentRenterPoints = 0;

		for (AbstractRental aRental : this) {
			thisAmount = aRental.rental();
			totalAmount += thisAmount;
			frequentRenterPoints += aRental.frequentRenterPoints();

			statement.append("\t").append(aRental.getMovie().getTitle())
					.append("\t").append(thisAmount).append("\n");
		}

		statement.append("Amount owed is ").append(totalAmount).append("\n");
		statement.append("You earned ").append(frequentRenterPoints)
				.append(" frequent renter points");

		return statement.toString();
	}
}
