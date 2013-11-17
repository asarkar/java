package name.abhijitsarkar.refactoring.chapter1;

import name.abhijitsarkar.refactoring.chapter1.Customer;
import name.abhijitsarkar.refactoring.chapter1.Movie;
import name.abhijitsarkar.refactoring.chapter1.Rental;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

	@Test
	public void testRegularMovieRentalForADay() {
		Customer cust = new Customer("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);

		Rental theShiningRentalForADay = new Rental(theShining, 1);
		cust.addRental(theShiningRentalForADay);

		String[] movieTitles = new String[] { theShining.getTitle() };
		double[] thisAmount = new double[] { 2.0d };
		double totalAmount = 2.0d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	@Test
	public void testRegularMovieRentalFor3Days() {
		Customer cust = new Customer("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);

		Rental theShiningRentalFor3Days = new Rental(theShining, 3);
		cust.addRental(theShiningRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle() };
		double[] thisAmount = new double[] { 3.5d };
		double totalAmount = 3.5d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	@Test
	public void test2RegularMovieRentalsFor3Days() {
		Customer cust = new Customer("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);
		Movie topGun = new Movie("Top Gun", Movie.REGULAR);

		Rental theShiningRentalFor3Days = new Rental(theShining, 3);
		cust.addRental(theShiningRentalFor3Days);

		Rental topGunRentalFor3Days = new Rental(topGun, 3);
		cust.addRental(topGunRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle(),
				topGun.getTitle() };
		double[] thisAmount = new double[] { 3.5d, 3.5d };
		double totalAmount = 7.0d;

		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	@Test
	public void testChildrensMovieRentalFor5Days() {
		Customer cust = new Customer("John Doe");
		Movie theLionKing = new Movie("The Lion King", Movie.CHILDRENS);

		Rental theLionKingRentalFor5Days = new Rental(theLionKing, 5);
		cust.addRental(theLionKingRentalFor5Days);

		String[] movieTitles = new String[] { theLionKing.getTitle() };
		double[] thisAmount = new double[] { 4.5d };
		double totalAmount = 4.5d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	@Test
	public void test1RegularAnd1ChidrensMovieRentalsFor3Days() {
		Customer cust = new Customer("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);
		Movie theLionKing = new Movie("The Lion King", Movie.CHILDRENS);

		Rental theShiningRentalFor3Days = new Rental(theShining, 3);
		cust.addRental(theShiningRentalFor3Days);

		Rental theLionKingRentalFor3Days = new Rental(theLionKing, 3);
		cust.addRental(theLionKingRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle(),
				theLionKing.getTitle() };
		double[] thisAmount = new double[] { 3.5d, 1.5d };
		double totalAmount = 5.0d;

		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	@Test
	public void testNewMovieRentalFor5Days() {
		Customer cust = new Customer("John Doe");
		Movie tdkRises = new Movie("The Dark Knight Rises", Movie.NEW_RELEASE);

		Rental tdkRisesRentalFor5Days = new Rental(tdkRises, 5);
		cust.addRental(tdkRisesRentalFor5Days);

		String[] movieTitles = new String[] { tdkRises.getTitle() };
		double[] thisAmount = new double[] { 15.0d };
		double totalAmount = 15.0d;
		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				cust.statement());
	}

	private String getRentalStatement(String custName, String[] movieTitles,
			double[] thisAmount, double totalAmount, int frequentRenterPoints) {
		StringBuilder statement = new StringBuilder();
		statement.append("Rental Record for ").append(custName).append("\n");

		for (int i = 0; i < movieTitles.length; i++) {
			statement.append("\t").append(movieTitles[i]).append("\t")
					.append(thisAmount[i]).append("\n");
		}

		statement.append("Amount owed is ").append(totalAmount).append("\n");
		statement.append("You earned ").append(frequentRenterPoints)
				.append(" frequent renter points");

		return statement.toString();
	}
}
