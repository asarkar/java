package name.abhijitsarkar.codinginterview.ooo.parking;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import name.abhijitsarkar.codinginterview.ooo.parking.Car.TypeOfCar;

public class Garage {
	private final List<Level> levels;

	public Garage(List<Level> levels) {
		this.levels = levels;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public static class Level {
		private final String id;
		private final List<TypeOfCar> typeOfCars;
		private final int capacity;
		private final AtomicInteger numberOfCarsParked;

		public Level(final String id, final List<TypeOfCar> typeOfCars, final int capacity) {
			this.id = id;
			this.typeOfCars = typeOfCars;
			this.capacity = capacity;
			this.numberOfCarsParked = new AtomicInteger(0);
		}

		public String getId() {
			return id;
		}

		public List<TypeOfCar> getTypeOfCars() {
			return typeOfCars;
		}

		public int getCapacity() {
			return capacity;
		}

		public int getNumberOfCarsParked() {
			return numberOfCarsParked.get();
		}

		public int incrementNumberOfCarsParked() {
			return this.numberOfCarsParked.incrementAndGet();
		}

		public int decrementNumberOfCarsParked() {
			return this.numberOfCarsParked.decrementAndGet();
		}

		public boolean isFull() {
			return numberOfCarsParked.get() == capacity;
		}
	}
}
