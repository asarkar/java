package name.abhijitsarkar.java.java8impatient.lambda;

import static java.lang.Character.isWhitespace;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Stream.of;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ComparatorFactory {
    public enum ComparatorCustomizationOption {
	REVERSED, CASE_INSENSITIVE, SPACE_INSENSITIVE;
    }

    private static final SpaceInsensitiveComparator SPACE_INSENSITIVE_ORDER = new SpaceInsensitiveComparator();

    private static class SpaceInsensitiveComparator implements
	    Comparator<String>, Serializable {
	private static final long serialVersionUID = 43977305580825595L;

	public int compare(final String s1, final String s2) {
	    final int l1 = s1.length();
	    final int l2 = s2.length();

	    for (int i = 0, j = 0; i < l1 && j < l2;) {
		final char c1 = s1.charAt(i);
		final char c2 = s2.charAt(j);

		if (isWhitespace(c1)) {
		    i++;

		    continue;
		} else if (isWhitespace(c2)) {
		    j++;

		    continue;
		}

		if (c1 != c2) {
		    return c1 - c2;
		}
	    }

	    return 0;
	}
    }

    /**
     * Write a method that generates a {@code Comparator<String>} that can be
     * normal or reversed, case-sensitive or case-insensitive, space-sensitive
     * or space-insensitive, or any combination thereof. Your method should
     * return a lambda expression.
     * 
     * @param customizationOptions
     *            Comparator customization options.
     * @return Comparator.
     */
    public static Comparator<String> newComparator(
	    final ComparatorCustomizationOption... customizationOptions) {

	final Map<ComparatorCustomizationOption, Comparator<String>> map = getComparatorCustomizationOptionsMap();

	return of(customizationOptions).map(map::get)
		.reduce((c1, c2) -> c1.thenComparing(c2))
		.orElse(naturalOrder());
    }

    static Map<ComparatorCustomizationOption, Comparator<String>> getComparatorCustomizationOptionsMap() {
	final Map<ComparatorCustomizationOption, Comparator<String>> map = new HashMap<>();

	map.put(ComparatorCustomizationOption.REVERSED, reverseOrder());
	map.put(ComparatorCustomizationOption.CASE_INSENSITIVE,
		CASE_INSENSITIVE_ORDER);
	map.put(ComparatorCustomizationOption.SPACE_INSENSITIVE,
		SPACE_INSENSITIVE_ORDER);

	return map;
    }
}
