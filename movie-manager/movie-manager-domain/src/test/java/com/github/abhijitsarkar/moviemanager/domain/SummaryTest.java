package com.github.abhijitsarkar.moviemanager.domain;

import static com.github.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.UNKNOWN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Summary;

public class SummaryTest {
    private static Summary summary1, summary2, summary3, summary4, summary5,
	    summary6, summary7;

    @BeforeClass
    public static void oneTimeSetUp() {
	GenreSummary genreSummary1 = null;
	GenreSummary genreSummary2 = null;
	GenreSummary genreSummary3 = null;
	GenreSummary genreSummary4 = null;
	GenreSummary genreSummary5 = null;
	GenreSummary genreSummary6 = null;

	genreSummary1 = new GenreSummary();
	genreSummary1.setGenre(ACTION_AND_ADVENTURE);
	genreSummary1.setMovieTitleCount(1);
	genreSummary1.setSoapTitleCount(1);

	genreSummary2 = new GenreSummary();
	genreSummary2.setGenre(ACTION_AND_ADVENTURE);
	genreSummary2.setMovieTitleCount(1);
	genreSummary2.setSoapTitleCount(1);

	genreSummary3 = new GenreSummary();
	genreSummary3.setGenre(null);
	genreSummary3.setMovieTitleCount(1);
	genreSummary3.setSoapTitleCount(1);

	genreSummary4 = new GenreSummary();
	genreSummary4.setGenre(UNKNOWN);
	genreSummary4.setMovieTitleCount(1);
	genreSummary4.setSoapTitleCount(1);

	genreSummary5 = new GenreSummary();
	genreSummary5.setGenre(ACTION_AND_ADVENTURE);
	genreSummary5.setMovieTitleCount(2);
	genreSummary5.setSoapTitleCount(1);

	genreSummary6 = new GenreSummary();
	genreSummary6.setGenre(ACTION_AND_ADVENTURE);
	genreSummary6.setMovieTitleCount(1);
	genreSummary6.setSoapTitleCount(2);

	List<GenreSummary> genreSummaryList1 = new ArrayList<GenreSummary>();
	genreSummaryList1.add(genreSummary1);

	List<GenreSummary> genreSummaryList2 = new ArrayList<GenreSummary>();
	genreSummaryList2.add(genreSummary2);

	List<GenreSummary> genreSummaryList3 = new ArrayList<GenreSummary>();
	genreSummaryList3.add(genreSummary3);

	List<GenreSummary> genreSummaryList4 = new ArrayList<GenreSummary>();
	genreSummaryList4.add(genreSummary4);

	List<GenreSummary> genreSummaryList5 = new ArrayList<GenreSummary>();
	genreSummaryList5.add(genreSummary5);

	List<GenreSummary> genreSummaryList6 = new ArrayList<GenreSummary>();
	genreSummaryList6.add(genreSummary6);

	summary1 = new Summary();

	summary2 = new Summary();
	summary2.setGenreSummary(genreSummaryList1);

	summary3 = new Summary();
	summary3.setGenreSummary(genreSummaryList2);

	summary4 = new Summary();
	summary4.setGenreSummary(genreSummaryList3);

	summary5 = new Summary();
	summary5.setGenreSummary(genreSummaryList4);

	summary6 = new Summary();
	summary6.setGenreSummary(genreSummaryList5);

	summary7 = new Summary();
	summary7.setGenreSummary(genreSummaryList6);
    }

    @AfterClass
    public static void oneTimeTearDown() {
	summary1 = null;
	summary2 = null;
	summary3 = null;
	summary4 = null;
	summary5 = null;
	summary6 = null;
	summary7 = null;
    }

    @Test
    public void testEquals() {
	assertEquals(summary1, summary1);
	assertEquals(summary2, summary2);
	assertEquals(summary2, summary3);
	assertFalse(summary2.equals("abc"));
	assertFalse(summary2.equals(summary4));
	assertFalse(summary2.equals(summary5));
	assertFalse(summary2.equals(summary6));
	assertFalse(summary2.equals(summary7));
    }

    @Test
    public void testHashCode() {
	assertEquals(summary1.hashCode(), summary1.hashCode());
	assertEquals(summary2.hashCode(), summary2.hashCode());
	assertEquals(summary2.hashCode(), summary3.hashCode());
	assertTrue(summary2.hashCode() != "abc".hashCode());
	assertTrue(summary2.hashCode() != summary4.hashCode());
	assertTrue(summary2.hashCode() != summary5.hashCode());
	assertTrue(summary2.hashCode() != summary6.hashCode());
	assertTrue(summary2.hashCode() != summary7.hashCode());
    }
}
