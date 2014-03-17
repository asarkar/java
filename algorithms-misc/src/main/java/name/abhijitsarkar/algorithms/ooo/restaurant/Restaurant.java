package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.util.List;
import java.util.Map;

public class Restaurant {
	public static String name;
	public static Menu menu;
	public static Map<DayOfTheWeek, OperatingTime> operatingHours;

	private List<Staff> staff;

	private enum DayOfTheWeek {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
	}

	private static class OperatingTime {
		private String openingTime;
		private String closingTime;
	}
}
