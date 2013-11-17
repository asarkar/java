package name.abhijitsarkar.refactoring.chapter1;

import name.abhijitsarkar.refactoring.chapter1.AbstractRental;
import name.abhijitsarkar.refactoring.chapter1.Movie;
import name.abhijitsarkar.refactoring.chapter1.RegularRental;
import name.abhijitsarkar.refactoring.chapter1.Rentals;

import org.junit.Assert;
import org.junit.Test;

public class RentalsTest {

	@Test
	public void testRentals() {
		Rentals rentals = new Rentals("John Doe");

		Assert.assertEquals("Wrong size", 0, rentals.size());

		Movie theShining = new Movie("The Shining", Movie.REGULAR);
		AbstractRental theShiningRentalForADay = new RegularRental(theShining,
				1);
		rentals.add(theShiningRentalForADay);

		Movie topGun = new Movie("Top Gun", Movie.REGULAR);
		AbstractRental topGunRentalFor3Days = new RegularRental(topGun, 3);
		rentals.add(topGunRentalFor3Days);

		Assert.assertEquals("Wrong size", 2, rentals.size());

		AbstractRental firstRental = rentals.get(0);
		AbstractRental secondRental = rentals.get(1);

		Assert.assertEquals("Wrong number of rental days", 1,
				firstRental.getDaysRented());
		Assert.assertEquals("Wrong number of rental days", 3,
				secondRental.getDaysRented());

		Assert.assertEquals("Wrong movie title", "The Shining", firstRental
				.getMovie().getTitle());
		Assert.assertEquals("Wrong movie title", "Top Gun", secondRental
				.getMovie().getTitle());
	}
}
