package name.abhijitsarkar.algorithms.ooo.restaurant;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.abhijitsarkar.algorithms.ooo.restaurant.Table.SpecialFeature;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.TransformerUtils;

public class TableManager {
	private static Set<Table> allTables;
	private static final Set<Integer> availableTableIds;

	static {
		availableTableIds = Collections.synchronizedSet(new HashSet<Integer>());
	}

	public static void setAllTables(Set<Table> allTables) {
		TableManager.allTables = allTables;

		final Set<Integer> availableTableIds = new HashSet<>(CollectionUtils.collect(allTables,
				TransformerUtils.<Table, Integer> invokerTransformer("tableId")));

		addAvailableTableIds(availableTableIds);
	}

	private static void addAvailableTableIds(Set<Integer> availableTableIds) {
		TableManager.availableTableIds.addAll(availableTableIds);
	}

	public static Table reserveTable(final int seating, final SpecialFeature... specialFeatures) {
		List<SpecialFeature> tableFeatures = null;
		Table table = null;

		outer: for (int availableTableId : availableTableIds) {
			table = findTableById(availableTableId);

			if (table.seatingCapacity() == seating) {
				tableFeatures = table.specialFeatures();

				for (SpecialFeature requestedFeature : specialFeatures) {
					if (!tableFeatures.contains(requestedFeature)) {
						continue outer;
					}
				}

				availableTableIds.remove(availableTableId);

				break;
			}

			table = null;
		}

		return table;
	}

	private static Table findTableById(final int availableTableId) {
		assertAtLeastOneTable();

		return CollectionUtils.find(allTables, new Predicate<Table>() {
			@Override
			public boolean evaluate(Table table) {
				return table.tableId() == availableTableId;
			}
		});
	}

	private static void assertAtLeastOneTable() {
		if (allTables == null || allTables.isEmpty()) {
			throw new IllegalStateException("The restaurant must have at least one table to be able to operate.");
		}
	}
}
