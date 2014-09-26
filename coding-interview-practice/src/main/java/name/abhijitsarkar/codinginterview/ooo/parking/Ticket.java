package name.abhijitsarkar.codinginterview.ooo.parking;

import java.time.LocalDateTime;

import name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar;

public class Ticket {

	private final String id;
	private final LocalDateTime entryTimestamp;
	private final TypeOfCar typeOfCar;
	private final String levelId;

	public Ticket(final String id, final LocalDateTime entryTimestamp, final TypeOfCar typeOfCar, final String levelId) {
		this.id = id;
		this.entryTimestamp = entryTimestamp;
		this.typeOfCar = typeOfCar;
		this.levelId = levelId;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public TypeOfCar getTypeOfCar() {
		return typeOfCar;
	}

	public String getLevelId() {
		return this.levelId;
	}
}
