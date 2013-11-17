package name.abhijitsarkar.refactoring.chapter1;

public class NewReleaseRental extends AbstractRental {

	public NewReleaseRental(Movie movie, int daysRented) {
		super(movie, daysRented);
	}

	@Override
	public double rental() {
		double rentalAmount = getDaysRented() * 3.0d;

		return rentalAmount;
	}

	@Override
	public int frequentRenterPoints() {
		return 2;
	}
}
