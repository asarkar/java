package name.abhijitsarkar.moviemanager.domain;

import static name.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static name.abhijitsarkar.moviemanager.domain.Genre.UNKNOWN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import name.abhijitsarkar.moviemanager.domain.GenreSummary;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenreSummaryTest {
	private static GenreSummary genreSummary1, genreSummary2, genreSummary3,
			genreSummary4, genreSummary5, genreSummary6;

	@BeforeClass
	public static void oneTimeSetUp() {
		genreSummary1 = new GenreSummary();
		genreSummary1.setGenre(ACTION_AND_ADVENTURE.toString());
		genreSummary1.setMovieTitleCount(1);
		genreSummary1.setSoapTitleCount(1);

		genreSummary2 = new GenreSummary();
		genreSummary2.setGenre(ACTION_AND_ADVENTURE.toString());
		genreSummary2.setMovieTitleCount(1);
		genreSummary2.setSoapTitleCount(1);

		genreSummary3 = new GenreSummary();
		genreSummary3.setGenre(null);
		genreSummary3.setMovieTitleCount(1);
		genreSummary3.setSoapTitleCount(1);

		genreSummary4 = new GenreSummary();
		genreSummary4.setGenre(UNKNOWN.toString());
		genreSummary4.setMovieTitleCount(1);
		genreSummary4.setSoapTitleCount(1);

		genreSummary5 = new GenreSummary();
		genreSummary5.setGenre(ACTION_AND_ADVENTURE.toString());
		genreSummary5.setMovieTitleCount(2);
		genreSummary5.setSoapTitleCount(1);

		genreSummary6 = new GenreSummary();
		genreSummary6.setGenre(ACTION_AND_ADVENTURE.toString());
		genreSummary6.setMovieTitleCount(1);
		genreSummary6.setSoapTitleCount(2);
	}

	@AfterClass
	public static void oneTimeTearDown() {
		genreSummary1 = null;
		genreSummary2 = null;
		genreSummary3 = null;
		genreSummary4 = null;
		genreSummary5 = null;
		genreSummary6 = null;
	}

	@Test
	public void testEquals() {
		assertEquals(genreSummary1, genreSummary1);
		assertEquals(genreSummary1, genreSummary2);
		assertFalse(genreSummary1.equals("abc"));
		assertFalse(genreSummary1.equals(genreSummary3));
		assertFalse(genreSummary1.equals(genreSummary4));
		assertFalse(genreSummary1.equals(genreSummary5));
		assertFalse(genreSummary1.equals(genreSummary6));
	}

	@Test
	public void testHashCode() {
		assertEquals(genreSummary1.hashCode(), genreSummary1.hashCode());
		assertEquals(genreSummary1.hashCode(), genreSummary2.hashCode());
		assertTrue(genreSummary1.hashCode() != "abc".hashCode());
		assertTrue(genreSummary1.hashCode() != genreSummary3.hashCode());
		assertTrue(genreSummary1.hashCode() != genreSummary4.hashCode());
		assertTrue(genreSummary1.hashCode() != genreSummary5.hashCode());
		assertTrue(genreSummary1.hashCode() != genreSummary6.hashCode());
	}
}
