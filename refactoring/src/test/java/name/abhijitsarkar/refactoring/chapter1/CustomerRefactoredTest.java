package name.abhijitsarkar.refactoring.chapter1;

import name.abhijitsarkar.refactoring.chapter1.AbstractRental;
import name.abhijitsarkar.refactoring.chapter1.ChildrensRental;
import name.abhijitsarkar.refactoring.chapter1.CustomerRefactored;
import name.abhijitsarkar.refactoring.chapter1.Movie;
import name.abhijitsarkar.refactoring.chapter1.NewReleaseRental;
import name.abhijitsarkar.refactoring.chapter1.RegularRental;
import name.abhijitsarkar.refactoring.chapter1.Rentals;

import org.junit.Assert;
import org.junit.Test;

public class CustomerRefactoredTest {

	@Test
	public void testRegularMovieRentalForADay() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);

		Rentals rentals = new Rentals(cust.getName());
		AbstractRental theShiningRentalForADay = new RegularRental(theShining,
				1);
		rentals.add(theShiningRentalForADay);

		String[] movieTitles = new String[] { theShining.getTitle() };
		double[] thisAmount = new double[] { 2.0d };
		double totalAmount = 2.0d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
	}

	@Test
	public void testRegularMovieRentalFor3Days() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);

		Rentals rentals = new Rentals(cust.getName());
		AbstractRental theShiningRentalFor3Days = new RegularRental(theShining,
				3);
		rentals.add(theShiningRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle() };
		double[] thisAmount = new double[] { 3.5d };
		double totalAmount = 3.5d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
	}

	@Test
	public void test2RegularMovieRentalsFor3Days() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);
		Movie topGun = new Movie("Top Gun", Movie.REGULAR);

		Rentals rentals = new Rentals(cust.getName());

		AbstractRental theShiningRentalFor3Days = new RegularRental(theShining,
				3);
		rentals.add(theShiningRentalFor3Days);

		AbstractRental topGunRentalFor3Days = new RegularRental(topGun, 3);
		rentals.add(topGunRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle(),
				topGun.getTitle() };
		double[] thisAmount = new double[] { 3.5d, 3.5d };
		double totalAmount = 7.0d;

		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
	}

	@Test
	public void testChildrensMovieRentalFor5Days() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie theLionKing = new Movie("The Lion King", Movie.CHILDRENS);

		Rentals rentals = new Rentals(cust.getName());

		AbstractRental theLionKingRentalFor5Days = new ChildrensRental(
				theLionKing, 5);
		rentals.add(theLionKingRentalFor5Days);

		String[] movieTitles = new String[] { theLionKing.getTitle() };
		double[] thisAmount = new double[] { 4.5d };
		double totalAmount = 4.5d;
		int frequentRenterPoints = 1;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
	}

	@Test
	public void test1RegularAnd1ChidrensMovieRentalsFor3Days() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie theShining = new Movie("The Shining", Movie.REGULAR);
		Movie theLionKing = new Movie("The Lion King", Movie.CHILDRENS);

		Rentals rentals = new Rentals(cust.getName());

		AbstractRental theShiningRentalFor3Days = new RegularRental(theShining,
				3);
		rentals.add(theShiningRentalFor3Days);

		AbstractRental theLionKingRentalFor3Days = new ChildrensRental(
				theLionKing, 3);
		rentals.add(theLionKingRentalFor3Days);

		String[] movieTitles = new String[] { theShining.getTitle(),
				theLionKing.getTitle() };
		double[] thisAmount = new double[] { 3.5d, 1.5d };
		double totalAmount = 5.0d;

		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
	}

	@Test
	public void testNewMovieRentalFor5Days() {
		CustomerRefactored cust = new CustomerRefactored("John Doe");
		Movie tdkRises = new Movie("The Dark Knight Rises", Movie.NEW_RELEASE);

		Rentals rentals = new Rentals(cust.getName());

		AbstractRental tdkRisesRentalFor5Days = new NewReleaseRental(tdkRises,
				5);
		rentals.add(tdkRisesRentalFor5Days);

		String[] movieTitles = new String[] { tdkRises.getTitle() };
		double[] thisAmount = new double[] { 15.0d };
		double totalAmount = 15.0d;
		int frequentRenterPoints = 2;

		String expectedStatement = getRentalStatement(cust.getName(),
				movieTitles, thisAmount, totalAmount, frequentRenterPoints);

		Assert.assertEquals("Wrong statement", expectedStatement,
				rentals.rentalStatement());
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
