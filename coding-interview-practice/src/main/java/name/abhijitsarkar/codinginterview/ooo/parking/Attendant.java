package name.abhijitsarkar.codinginterview.ooo.parking;

import java.time.LocalDateTime;
import java.util.Optional;

import name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar;
import name.abhijitsarkar.codinginterview.ooo.parking.Garage.Level;

public class Attendant {
	private BillingSystem billingSystem;
	private Garage garage;

	public Attendant(BillingSystem billingSystem, Garage garage) {
		this.billingSystem = billingSystem;
		this.garage = garage;
	}

	public Ticket generateTicketAndOpenGate(final Car car) {
		final Level level = findParkingLevel(car.getType());

		Ticket ticket = new Ticket(car.getLicensePlateNumber(), LocalDateTime.now(), car.getType(), level.getId());

		openGate(true, level.getId());

		return ticket;
	}

	private void openGate(final boolean isEntry, final String levelId) {
		final Level level = findParkingLevel(levelId);

		if (isEntry) {
			level.incrementNumberOfCarsParked();
		} else {
			level.decrementNumberOfCarsParked();
		}
	}

	private Level findParkingLevel(final TypeOfCar type) {
		Optional<Level> optional = garage.getLevels().stream()
				.filter(level -> level.getTypeOfCars().contains(type) && !level.isFull()).findFirst();

		/* JDK bug JDK-8054569, http://stackoverflow.com/questions/25523375/java8-lambdas-and-exceptions*/
		return optional.<RuntimeException> orElseThrow(() -> {
			throw new NoParkingSpaceAvailable("Sorry, no parking space is available for a " + type + " car");
		});
	}

	public Receipt acceptPaymentAndOpenGate(final Ticket ticket, final CreditCard creditCard) {
		final Receipt receipt = billingSystem.generateBill(ticket);
		final String transactionId = billingSystem.chargeCreditCard(receipt.getTotal(), creditCard);

		receipt.setTransactionId(transactionId);

		openGate(false, ticket.getLevelId());

		return receipt;
	}

	private Level findParkingLevel(final String levelId) {
		return garage.getLevels().stream().filter(level -> level.getId().equals(levelId)).findFirst().get();
	}

	public BillingSystem getBillingSystem() {
		return billingSystem;
	}

	public void setBillingSystem(BillingSystem billingSystem) {
		this.billingSystem = billingSystem;
	}

	public Garage getGarage() {
		return garage;
	}

	public void setGarage(Garage garage) {
		this.garage = garage;
	}
}
