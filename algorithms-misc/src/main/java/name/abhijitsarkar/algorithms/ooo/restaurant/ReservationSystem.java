package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import name.abhijitsarkar.algorithms.ooo.restaurant.Table.SpecialFeature;

public class ReservationSystem {
	private static final Map<String, List<Reservation>> allReservations;
	private static final AtomicInteger reservationNumber;

	private static final String DATE_FORMAT;

	static {
		allReservations = new HashMap<String, List<Reservation>>();
		reservationNumber = new AtomicInteger();

		DATE_FORMAT = "yyyy-DD-dd";
	}

	public static int makeReservation(final String customerName, final int seating, final Date date,
			final SpecialFeature... specialFeatures) {
		final Table table = TableManager.reserveTable(seating, specialFeatures);
		String formattedDate = null;

		if (table != null) {
			final Reservation r = new Reservation(reservationNumber.incrementAndGet(), customerName);
			r.setTables(Arrays.asList(new Table[] { table }));
			r.setDate(date);

			formattedDate = format(date);

			synchronized (ReservationSystem.class) {
				List<Reservation> allReservationsForADay = allReservations.get(formattedDate);

				if (allReservationsForADay == null) {
					allReservationsForADay = new ArrayList<>();
				}

				allReservationsForADay.add(r);

				allReservations.put(formattedDate, allReservationsForADay);
			}

			return r.reservationNumber();
		}

		final StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("No table is available on ").append(formattedDate).append(" with seating ").append(seating)
				.append(" and features ").append(specialFeatures).append(".");

		throw new UnableToCompleteReservationException(errorMessage.toString());
	}

	private static String format(final Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

	public static boolean cancelReservation(final String customerName, final Date date) {
		boolean cancelledReservation = false;

		final String formattedDate = format(date);

		synchronized (ReservationSystem.class) {
			final List<Reservation> allReservationsForADay = allReservations.get(formattedDate);

			if (allReservationsForADay != null) {
				for (final Reservation r : allReservationsForADay) {
					if (isMatchingReservation(r, customerName, formattedDate)) {
						allReservationsForADay.remove(r);

						allReservations.put(formattedDate, allReservationsForADay);

						cancelledReservation = true;

						break;
					}
				}
			}
		}

		return cancelledReservation;
	}

	private static boolean isMatchingReservation(final Reservation r, final String customerName,
			final String formattedDate) {
		return r.customerName().equalsIgnoreCase(customerName) && format(r.date()).equals(formattedDate);
	}
}
