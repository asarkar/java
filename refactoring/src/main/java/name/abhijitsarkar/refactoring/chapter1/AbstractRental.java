package name.abhijitsarkar.refactoring.chapter1;

abstract class AbstractRental {
	private Movie movie;
	private int daysRented;

	public AbstractRental(Movie movie, int daysRented) {
		this.movie = movie;
		this.daysRented = daysRented;
	}

	public int getDaysRented() {
		return daysRented;
	}

	public Movie getMovie() {
		return movie;
	}

	public int frequentRenterPoints() {
		return 1;
	}

	public abstract double rental();
}
