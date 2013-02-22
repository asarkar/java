package com.github.abhijitsarkar.moviemanager.service.impl;

import static com.github.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.DOCUMENTARY;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.HORROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.service.mock.MovieServiceMock;

public class MovieServiceImplTest {

	private static MovieService movieService;
	private static SortedSet<Movie> movieSet;

	@Before
	public void setUp() throws IOException {
		movieService = new MovieServiceMock();
		movieSet = movieService.getMovieSet(null);
	}

	@AfterClass
	public static void oneTimeTearDown() {
		movieService = null;

		movieSet = null;
	}

	@Test
	public void testIsParentNotASeries() {
		assertFalse("Testing \"Criminal Minds\"",
				this.isParentNotASeries("Criminal Minds"));
		assertFalse("Testing \"\"", this.isParentNotASeries(""));
		assertFalse("Testing \" \"", this.isParentNotASeries(" "));
		assertFalse("Testing null", this.isParentNotASeries(null));
		assertTrue("Testing \"Tesis (1996)\"",
				this.isParentNotASeries("Tesis (1996)"));
	}

	private boolean isParentNotASeries(String parent) {
		return (parent == null) ? false : parent.trim().matches(
				".*(?:\\((\\d{4})\\))$");
	}

	@Test
	public void testFilterMovieSetByGenre() {
		SortedSet<Movie> movieSetFilteredByGenre = null;
		Movie movie = null;

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				ACTION_AND_ADVENTURE);
		movie = new Movie("Casino Royal", (short) 2006, ACTION_AND_ADVENTURE,
				1571095307L, ".mkv");
		assertEquals("Incorrect " + ACTION_AND_ADVENTURE + " size", 5,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				HORROR);
		movie = new Movie("Inferno", (short) 1980, HORROR, 2216523351L, ".mkv");
		assertEquals("Incorrect " + HORROR + " size", 6,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				DOCUMENTARY);
		assertEquals("Incorrect " + DOCUMENTARY + " size", 0,
				movieSetFilteredByGenre.size());
	}

	@Test
	public void testGroupMovieSetByGenre() {
		Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre = null;
		SortedSet<Movie> movieSetFilteredByGenre = null;
		Movie movie = null;

		movieSetGroupedByGenre = movieService.groupMovieSetByGenre(movieSet);

		movieSetFilteredByGenre = movieSetGroupedByGenre
				.get(ACTION_AND_ADVENTURE);
		movie = new Movie("Casino Royal", (short) 2006, ACTION_AND_ADVENTURE,
				1571095307L, ".mkv");
		assertEquals("Incorrect " + ACTION_AND_ADVENTURE + " size", 5,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieSetGroupedByGenre.get(HORROR);
		movie = new Movie("Inferno", (short) 1980, HORROR, 2216523351L, ".mkv");
		assertEquals("Incorrect " + HORROR + " size", 6,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieSetGroupedByGenre.get(DOCUMENTARY);
		assertEquals("Incorrect " + DOCUMENTARY + " size", 0,
				movieSetFilteredByGenre.size());
	}

	@Test
	public void testGetSummaryByGenre() {
		GenreSummary genreSummary = null;

		genreSummary = movieService.getSummaryByGenre(movieService
				.filterMovieSetByGenre(movieSet, ACTION_AND_ADVENTURE),
				ACTION_AND_ADVENTURE);

		assertEquals("Incorrect movieTitleCount for " + ACTION_AND_ADVENTURE,
				5, genreSummary.getMovieTitleCount());
		assertEquals("Incorrect soapTitleCount for " + ACTION_AND_ADVENTURE, 0,
				genreSummary.getSoapTitleCount());

		genreSummary = movieService.getSummaryByGenre(
				movieService.filterMovieSetByGenre(movieSet, HORROR), HORROR);

		assertEquals("Incorrect movieTitleCount for " + HORROR, 5,
				genreSummary.getMovieTitleCount());
		assertEquals("Incorrect soapTitleCount for " + HORROR, 0,
				genreSummary.getSoapTitleCount());
	}

	@Test
	public void testGetSummary() {
		Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre = movieService
				.groupMovieSetByGenre(movieSet);
		Summary summary = null;
		GenreSummary genreSummary = null;

		summary = movieService.getSummary(movieSetGroupedByGenre);

		genreSummary = new GenreSummary();
		genreSummary.setGenre(ACTION_AND_ADVENTURE);
		genreSummary.setMovieTitleCount(5);
		genreSummary.setSoapTitleCount(0);

		assertTrue("Summary does not contain genre summary for "
				+ ACTION_AND_ADVENTURE,
				summary.getGenreSummary().contains(genreSummary));

		genreSummary = new GenreSummary();
		genreSummary.setGenre(HORROR);
		genreSummary.setMovieTitleCount(5);
		genreSummary.setSoapTitleCount(0);

		assertTrue("Summary does not contain genre summary for " + HORROR,
				summary.getGenreSummary().contains(genreSummary));
	}

	@Test
	public void testGetParentWhenValidGenre() {
		try {
			SortedSet<Movie> movieSet = movieService.getMovieSet(new File(
					"src/test/resources/Movies/").getAbsolutePath());
			assertTrue("Movie set must not be empty.", movieSet.size() > 0);

			Movie aMovie = new Movie("SomeThriller", (short) -1,
					Genre.THRILLER, -1L, ".mkv");

			if (movieSet.contains(aMovie)) {
				for (Movie m : movieSet) {
					if (m.equals(aMovie)) {
						aMovie = m;

						break;
					}
				}
			} else {
				fail("Movie set should have contained \"SomeThriller\".");
			}

			assertNotNull("Null parent for movie \"SomeThriller\".",
					aMovie.getParent());

			assertEquals("Wrong parent for movie \"SomeThriller\".", "Another",
					aMovie.getParent());
		} catch (IOException ex) {
			fail("Should not be here.");
		}
	}

	@Test
	public void testGetParentWhenNoGenre() {
		try {
			SortedSet<Movie> movieSet = movieService.getMovieSet(new File(
					"src/test/resources/Movies/").getAbsolutePath());
			assertTrue("Movie set must not be empty.", movieSet.size() > 0);

			Movie aMovie = new Movie("SomeMovie", (short) -1, Genre.UNKNOWN,
					-1L, ".mkv");

			if (movieSet.contains(aMovie)) {
				for (Movie m : movieSet) {
					if (m.equals(aMovie)) {
						aMovie = m;

						break;
					}
				}
			} else {
				fail("Movie set should have contained \"SomeMovie\".");
			}

			assertNotNull("Null parent for movie \"SomeMovie\".",
					aMovie.getParent());

			assertEquals("Wrong parent for movie \"SomeMovie\".", "Another",
					aMovie.getParent());
		} catch (IOException ex) {
			fail("Should not be here.");
		}
	}
}
