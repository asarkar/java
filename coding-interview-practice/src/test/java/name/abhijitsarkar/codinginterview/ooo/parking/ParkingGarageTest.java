package name.abhijitsarkar.codinginterview.ooo.parking;

import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.COMPACT;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.HYBRID;
import static name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar.SUV;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ParkingGarageTest {
	private ParkingGarage parkingGarage = new ParkingGarage();

	@Test
	public void testParkingOneCompact() {
		Car compact = new Car(COMPACT, "abc123");

		Ticket ticket = parkingGarage.getAttendant().generateTicketAndOpenGate(compact);

		assertNotNull(ticket.getId());

		assertEquals(COMPACT, ticket.getTypeOfCar());
		assertEquals("1", ticket.getLevelId());

		Receipt receipt = parkingGarage.getAttendant().acceptPaymentAndOpenGate(ticket, null);

		verifyReceipt(receipt, ticket);
	}

	private void verifyReceipt(Receipt receipt, Ticket ticket) {
		assertEquals(ticket.getId(), receipt.getTicketId());
		assertNotNull(receipt.getTransactionId());
		assertEquals(5.0f, receipt.getHourlyRate(), 0.1f);
		assertEquals(1.0f, receipt.getTaxesAndOtherSurcharges(), 0.1f);
		assertEquals(5.0f, receipt.getHourlyRate(), 0.1f);
		assertEquals(6.0f, receipt.getTotal(), 0.1f);
		assertEquals(1, receipt.getHoursParked());
	}

	@Test
	public void testParkingOneHybrid() {
		Car hybrid = new Car(HYBRID, "abc123");

		Ticket ticket = parkingGarage.getAttendant().generateTicketAndOpenGate(hybrid);

		assertNotNull(ticket.getId());

		assertEquals(HYBRID, ticket.getTypeOfCar());
		assertEquals("1", ticket.getLevelId());

		Receipt receipt = parkingGarage.getAttendant().acceptPaymentAndOpenGate(ticket, null);

		verifyReceipt(receipt, ticket);
	}

	@Test(expected = NoParkingSpaceAvailable.class)
	public void testParkingOneCompactAndHybrid() {
		Car compact = new Car(COMPACT, "abc123");

		parkingGarage.getAttendant().generateTicketAndOpenGate(compact);

		Car hybrid = new Car(HYBRID, "abc123");

		parkingGarage.getAttendant().generateTicketAndOpenGate(hybrid);
	}

	@Test
	public void testParkingOneSUV() {
		Car hybrid = new Car(SUV, "abc123");

		Ticket ticket = parkingGarage.getAttendant().generateTicketAndOpenGate(hybrid);

		assertNotNull(ticket.getId());

		assertEquals(SUV, ticket.getTypeOfCar());
		assertEquals("3", ticket.getLevelId());

		Receipt receipt = parkingGarage.getAttendant().acceptPaymentAndOpenGate(ticket, null);

		verifyReceipt(receipt, ticket);
	}
}
