package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.util.Date;
import java.util.List;

public class Reservation {
	private final int reservationNumber;
	private final String customerName;
	private List<Table> tables;
	private Date date;

	public Reservation(final int reservationNumber, final String customerName) {
		this.reservationNumber = reservationNumber;
		this.customerName = customerName;
	}

	public List<Table> tables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public void addTables(List<Table> tables) {
		this.tables.addAll(tables);
	}

	public Date date() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int reservationNumber() {
		return reservationNumber;
	}

	public String customerName() {
		return customerName;
	}

	@Override
	public String toString() {
		return "Reservation [reservationNumber=" + reservationNumber + ", customerName=" + customerName + ", tables="
				+ tables + ", date=" + date + "]";
	}
}
