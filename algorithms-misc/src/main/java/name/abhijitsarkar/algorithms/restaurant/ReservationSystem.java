package name.abhijitsarkar.algorithms.restaurant;

import java.util.Date;
import java.util.Queue;

import name.abhijitsarkar.algorithms.restaurant.Table.SpecialFeature;

public class ReservationSystem {
	private static Queue<Reservation> reservations;

	public static int makeReservation(String customerName, int seating, Date date, SpecialFeature... specialFeatures) {
		return -1;
	}

	public static boolean cancelReservation(String customerName, Date date) {
		final int reservationNumber = lookupReservation(customerName, date);

		return cancelReservation(reservationNumber);
	}

	private static int lookupReservation(String customerName, Date date) {
		return -1;
	}

	public static boolean cancelReservation(int reservationNumber) {
		return true;
	}
}
