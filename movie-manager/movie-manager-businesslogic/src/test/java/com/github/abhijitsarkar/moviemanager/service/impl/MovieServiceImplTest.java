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
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.service.mock.MovieServiceMock;

public class MovieServiceImplTest {

	private static MovieService movieService;
	private static Set<Movie> movieSet;

	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
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
		Set<Movie> movieSetFilteredByGenre = null;
		Movie movie = null;

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				ACTION_AND_ADVENTURE.toString());
		movie = new Movie("Casino Royal", (short) 2006,
				ACTION_AND_ADVENTURE.toString(), 1571095307L, ".mkv");
		assertEquals("Incorrect " + ACTION_AND_ADVENTURE.toString() + " size",
				5, movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				HORROR.toString());
		movie = new Movie("Inferno", (short) 1980, HORROR.toString(),
				2216523351L, ".mkv");
		assertEquals("Incorrect " + HORROR.toString() + " size", 6,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieService.filterMovieSetByGenre(movieSet,
				DOCUMENTARY.toString());
		assertEquals("Incorrect " + DOCUMENTARY.toString() + " size", 0,
				movieSetFilteredByGenre.size());
	}

	@Test
	public void testGroupMovieSetByGenre() {
		Map<String, Set<Movie>> movieSetGroupedByGenre = null;
		Set<Movie> movieSetFilteredByGenre = null;
		Movie movie = null;

		movieSetGroupedByGenre = movieService.groupMovieSetByGenre(movieSet);

		movieSetFilteredByGenre = movieSetGroupedByGenre
				.get(ACTION_AND_ADVENTURE.toString());
		movie = new Movie("Casino Royal", (short) 2006,
				ACTION_AND_ADVENTURE.toString(), 1571095307L, ".mkv");
		assertEquals("Incorrect " + ACTION_AND_ADVENTURE.toString() + " size",
				5, movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieSetGroupedByGenre.get(HORROR.toString());
		movie = new Movie("Inferno", (short) 1980, HORROR.toString(),
				2216523351L, ".mkv");
		assertEquals("Incorrect " + HORROR.toString() + " size", 6,
				movieSetFilteredByGenre.size());
		assertTrue("Filtered set does not contain movie " + movie,
				movieSetFilteredByGenre.contains(movie));

		movieSetFilteredByGenre = movieSetGroupedByGenre.get(DOCUMENTARY
				.toString());
		assertEquals("Incorrect " + DOCUMENTARY.toString() + " size", 0,
				movieSetFilteredByGenre.size());
	}

	@Test
	public void testGetSummaryByGenre() {
		GenreSummary genreSummary = null;

		genreSummary = movieService.getSummaryByGenre(movieService
				.filterMovieSetByGenre(movieSet,
						ACTION_AND_ADVENTURE.toString()), ACTION_AND_ADVENTURE
				.toString());

		assertEquals(
				"Incorrect movieTitleCount for "
						+ ACTION_AND_ADVENTURE.toString(), 5,
				genreSummary.getMovieTitleCount());
		assertEquals(
				"Incorrect soapTitleCount for "
						+ ACTION_AND_ADVENTURE.toString(), 0,
				genreSummary.getSoapTitleCount());

		genreSummary = movieService
				.getSummaryByGenre(
						movieService.filterMovieSetByGenre(movieSet,
								HORROR.toString()), HORROR.toString());

		assertEquals("Incorrect movieTitleCount for " + HORROR.toString(), 6,
				genreSummary.getMovieTitleCount());
		assertEquals("Incorrect soapTitleCount for " + HORROR.toString(), 0,
				genreSummary.getSoapTitleCount());
	}

	@Test
	public void testGetSummary() {
		Map<String, Set<Movie>> movieSetGroupedByGenre = movieService
				.groupMovieSetByGenre(movieSet);
		Summary summary = null;
		GenreSummary genreSummary = null;

		summary = movieService.getSummary(movieSetGroupedByGenre);

		genreSummary = new GenreSummary();
		genreSummary.setGenre(ACTION_AND_ADVENTURE.toString());
		genreSummary.setMovieTitleCount(5);
		genreSummary.setSoapTitleCount(0);

		assertTrue("Summary does not contain genre summary for "
				+ ACTION_AND_ADVENTURE.toString(), summary.getGenreSummary()
				.contains(genreSummary));

		genreSummary = new GenreSummary();
		genreSummary.setGenre(HORROR.toString());
		genreSummary.setMovieTitleCount(6);
		genreSummary.setSoapTitleCount(0);

		assertTrue(
				"Summary does not contain genre summary for "
						+ HORROR.toString(), summary.getGenreSummary()
						.contains(genreSummary));
	}

	@Test
	public void testGetParentWhenValidGenre() {
		try {
			Set<Movie> movieSet = movieService.getMovieSet(new File(
					"src/test/resources/Movies/").getAbsolutePath());
			assertTrue("Movie set must not be empty.", movieSet.size() > 0);

			Movie aMovie = new Movie("SomeThriller", (short) -1,
					Genre.THRILLER.toString(), -1L, ".mkv");

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
			Set<Movie> movieSet = movieService.getMovieSet(new File(
					"src/test/resources/Movies/").getAbsolutePath());
			assertTrue("Movie set must not be empty.", movieSet.size() > 0);

			Movie aMovie = new Movie("SomeMovie", (short) -1,
					Genre.UNKNOWN.toString(), -1L, ".mkv");

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
