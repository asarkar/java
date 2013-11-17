package name.abhijitsarkar.refactoring.chapter1;

public class RegularRental extends AbstractRental {

	public RegularRental(Movie movie, int daysRented) {
		super(movie, daysRented);
	}

	@Override
	public double rental() {
		double rentalAmount = 2.0d;
		
		if (getDaysRented() > 2) {
			rentalAmount += (getDaysRented() - 2) * 1.5d;
		}

		return rentalAmount;
	}
}
