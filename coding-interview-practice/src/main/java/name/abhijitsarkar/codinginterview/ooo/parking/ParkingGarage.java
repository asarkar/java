package name.abhijitsarkar.codinginterview.ooo.parking;

import static java.util.Arrays.asList;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.COMPACT;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.FULL_SIZE;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.HYBRID;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.MINIVAN;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.PICKUP_TRUCK;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.SUV;
import name.abhijitsarkar.codinginterview.ooo.parking.Garage.Level;

public class ParkingGarage {
	private final Attendant attendant;

	public ParkingGarage() {
		final Level firstLevel = new Level("1", asList(COMPACT, HYBRID), 1);
		final Level secondLevel = new Level("2", asList(FULL_SIZE), 1);
		final Level thirdLevel = new Level("3", asList(MINIVAN, SUV, PICKUP_TRUCK), 2);

		final Garage garage = new Garage(asList(firstLevel, secondLevel, thirdLevel));

		final Calculator calculator = new BasicCalculator();
		final BillingSystem billingSystem = new BasicBillingSystem(calculator);

		attendant = new Attendant(billingSystem, garage);
	}

	public Attendant getAttendant() {
		return attendant;
	}
}
