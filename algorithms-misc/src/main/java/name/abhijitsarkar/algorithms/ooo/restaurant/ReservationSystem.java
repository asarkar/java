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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationSystem {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSystem.class);

	private static final Map<String, List<Reservation>> allReservations;
	private static final AtomicInteger reservationNumber;

	private static final String DATE_FORMAT;

	static {
		allReservations = new HashMap<String, List<Reservation>>();
		reservationNumber = new AtomicInteger();

		DATE_FORMAT = "yyyy-MM-dd";
	}

	public static int makeReservation(final String customerName, final int seating, final Date date,
			final SpecialFeature... specialFeatures) {
		final Table table = TableManager.reserveTable(seating, specialFeatures);
		final String formattedDate = format(date);

		final StringBuilder message = new StringBuilder();

		if (table != null) {
			final Reservation r = new Reservation(reservationNumber.incrementAndGet(), customerName);
			r.setTables(Arrays.asList(new Table[] { table }));
			r.setDate(date);

			addToReservationsList(r, formattedDate);

			message.append("Made reservation for ").append(customerName).append(" for date ").append(formattedDate)
					.append(" with seating ").append(seating).append(" and features ").append(specialFeatures)
					.append(".");

			LOGGER.info(message.toString());

			return r.reservationNumber();
		}

		message.delete(0, message.length() + 1);

		message.append("No table is available on ").append(formattedDate).append(" with seating ").append(seating)
				.append(" and features ").append(specialFeatures).append(".");

		throw new UnableToCompleteReservationException(message.toString());
	}

	synchronized private static void addToReservationsList(final Reservation r, final String formattedDate) {

		List<Reservation> allReservationsForADay = allReservations.get(formattedDate);

		if (allReservationsForADay == null) {
			allReservationsForADay = new ArrayList<>();
		}

		allReservationsForADay.add(r);

		allReservations.put(formattedDate, allReservationsForADay);
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
				final Reservation r = lookUpReservation(customerName, date);

				if (r != null) {
					final StringBuilder message = new StringBuilder();

					message.append("Cancelled reservation for ").append(customerName).append(" for date ")
							.append(formattedDate).append(".");

					LOGGER.info(message.toString());

					allReservationsForADay.remove(r);

					cancelledReservation = true;
				}
			}
		}

		return cancelledReservation;
	}

	static Reservation lookUpReservation(final String customerName, final Date date) {
		final String formattedDate = format(date);
		Reservation res = null;

		final List<Reservation> allReservationsForADay = allReservations.get(formattedDate);

		for (final Reservation r : allReservationsForADay) {
			if (isMatchingReservation(r, customerName, formattedDate)) {
				final StringBuilder message = new StringBuilder();

				message.append("Found reservation for ").append(customerName).append(" for date ")
						.append(formattedDate).append(".");

				LOGGER.info(message.toString());

				res = r;

				break;
			}
		}

		return res;
	}

	private static boolean isMatchingReservation(final Reservation r, final String customerName,
			final String formattedDate) {
		return r.customerName().equalsIgnoreCase(customerName) && format(r.date()).equals(formattedDate);
	}
}
