package com.github.abhijitsarkar.moviemanager.domain;

import static com.github.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.UNKNOWN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.abhijitsarkar.moviemanager.service.impl.MovieComparator;

public class MovieTest {
	private static Movie movie1, movie2, movie3, movie4, movie5, movie6,
			movie7;

	@BeforeClass
	public static void oneTimeSetUp() {
		movie1 = new Movie("3-10 To Yuma", (short) 2007,
				ACTION_AND_ADVENTURE.toString(), 2250491151L, ".mkv");
		movie2 = new Movie("Body of Lies", (short) 2008,
				ACTION_AND_ADVENTURE.toString(), 2233486674L, ".mkv");
		movie3 = new Movie(null, (short) 2007, ACTION_AND_ADVENTURE.toString(),
				2250491151L, ".mkv");
		movie4 = new Movie("3-10 To Yuma", (short) -1,
				ACTION_AND_ADVENTURE.toString(), 2250491151L, ".mkv");
		movie5 = new Movie("3-10 To Yuma", (short) 2007, UNKNOWN.toString(),
				2250491151L, ".mkv");
		movie6 = new Movie("3-10 To Yuma", (short) 2007, null, 2250491151L,
				".mkv");
		movie7 = new Movie("3-10 To Yuma", (short) 2007,
				ACTION_AND_ADVENTURE.toString(), 2250491151L, ".mkv");
	}

	@AfterClass
	public static void oneTimeTearDown() {
		movie1 = null;
		movie2 = null;
		movie3 = null;
		movie4 = null;
		movie5 = null;
		movie6 = null;
		movie7 = null;
	}

	@Test
	public void testEquals() {
		assertEquals(movie1, movie1);
		assertEquals(movie1, movie7);
		assertFalse(movie1.equals("abc"));
		assertFalse(movie1.equals(movie2));
		assertFalse(movie1.equals(movie3));
		assertFalse(movie1.equals(movie4));
		assertFalse(movie1.equals(movie5));
		assertFalse(movie1.equals(movie6));
	}

	@Test
	public void testHashCode() {
		assertEquals(movie1.hashCode(), movie1.hashCode());
		assertEquals(movie1.hashCode(), movie7.hashCode());
		assertTrue(movie1.hashCode() != "abc".hashCode());
		assertTrue(movie1.hashCode() != movie2.hashCode());
		assertTrue(movie1.hashCode() != movie3.hashCode());
		assertTrue(movie1.hashCode() != movie4.hashCode());
		assertTrue(movie1.hashCode() != movie5.hashCode());
		assertTrue(movie1.hashCode() != movie6.hashCode());
	}

	@Test
	public void testListEquality() {
		SortedSet<Movie> movieList1 = new TreeSet<Movie>(
				new MovieComparator<Movie>());
		SortedSet<Movie> movieList2 = new TreeSet<Movie>(
				new MovieComparator<Movie>());
		SortedSet<Movie> movieList3 = new TreeSet<Movie>(
				new MovieComparator<Movie>());

		movieList1.add(movie1);
		movieList2.add(movie1);
		movieList3.add(movie3);

		assertEquals(movieList1, movieList1);
		assertEquals(movieList1, movieList2);
		assertFalse(movieList1.equals(movieList3));

	}
}
