package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.util.List;

public class Table {
	private final int tableId;
	private final int seatingCapacity;
	private List<SpecialFeature> specialFeatures;

	public enum SpecialFeature {
		WINDOW_SIDE;
	}

	public Table(int tableId, int seatingCapacity) {
		this.tableId = tableId;
		this.seatingCapacity = seatingCapacity;
	}

	public int tableId() {
		return tableId;
	}

	public int seatingCapacity() {
		return seatingCapacity;
	}

	public List<SpecialFeature> specialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(List<SpecialFeature> specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tableId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Table other = (Table) obj;
		if (tableId != other.tableId) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Table [tableId=" + tableId + ", seatingCapacity=" + seatingCapacity + ", specialFeatures="
				+ specialFeatures + "]";
	}
}
