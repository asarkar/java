package name.abhijitsarkar.refactoring.chapter1;

public class ChildrensRental extends AbstractRental {

	public ChildrensRental(Movie movie, int daysRented) {
		super(movie, daysRented);
	}

	@Override
	public double rental() {
		double rentalAmount = 1.5d;
		
		if (getDaysRented() > 3) {
			rentalAmount += (getDaysRented() - 3) * 1.5d;
		}

		return rentalAmount;
	}
}
