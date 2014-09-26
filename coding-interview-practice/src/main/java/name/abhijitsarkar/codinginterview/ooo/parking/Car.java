package name.abhijitsarkar.codinginterview.ooo.parking;

public class Car {

	private final TypeOfCar type;
	private final String licensePlateNumber;

	public Car(final TypeOfCar type, final String licensePlateNumber) {
		this.type = type;
		this.licensePlateNumber = licensePlateNumber;
	}

	public TypeOfCar getType() {
		return type;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	
	public enum TypeOfCar {
		COMPACT, FULL_SIZE, HYBRID, MINIVAN, SUV, PICKUP_TRUCK;
	}
}
