/* Copyright (c) 2013,the original author or authors.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see http://www.gnu.org/licenses. */

package name.abhijitsarkar.moviedatabase.api.client.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import name.abhijitsarkar.moviedatabase.api.client.AbstractContextAwareTest;
import name.abhijitsarkar.moviedatabase.api.client.dao.Client;
import name.abhijitsarkar.moviedatabase.api.client.domain.Movie;

/**
 * 
 * @author Abhijit Sarkar
 */
public class OmdbApiClientTest extends AbstractContextAwareTest {
	@Autowired
	@Qualifier("omdbApiClient")
	private Client client;

	@Before
	public void testAutowire() {
		assertNotNull(client);
	}

	@Test
	public void testGetMovieInfoByTitleAndYear() {
		@SuppressWarnings("unchecked")
		Movie omdbApiStyleMovie = client.getMovieInfoByTitleAndYear(
				"Star Trek", (short) 1996);
		assertNotNull("At least one Star Trek movie should've been found",
				omdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0117731",
				omdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek: First Contact",
				omdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 1996, omdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", omdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", omdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", omdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", omdbApiStyleMovie.getImdbVotes());

		System.out.println("testGetMovieInfoByTitleAndYear: "
				+ omdbApiStyleMovie);
	}

	@Test
	public void testGetMovieInfoById() {
		@SuppressWarnings("unchecked")
		Movie omdbApiStyleMovie = client.getMovieInfoById("tt0117731");
		assertNotNull("At least one Star Trek movie should've been found",
				omdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0117731",
				omdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek: First Contact",
				omdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 1996, omdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", omdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", omdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", omdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", omdbApiStyleMovie.getImdbVotes());

		System.out.println("testGetMovieInfoById: " + omdbApiStyleMovie);
	}

	@Test
	public void testGetMovieInfoByTitleOnly() {
		@SuppressWarnings("unchecked")
		Movie omdbApiStyleMovie = client
				.getMovieInfoByTitleOnly("Star Trek");
		assertNotNull("At least one Star Trek movie should've been found",
				omdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0796366",
				omdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek",
				omdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 2009, omdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", omdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", omdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", omdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", omdbApiStyleMovie.getImdbVotes());
		System.out.println("testGetMovieInfoByTitleOnly: "
				+ omdbApiStyleMovie);
	}

	@Test
	public void testGetNoSuchMovieInfoById() {
		@SuppressWarnings("unchecked")
		Movie omdbApiStyleMovie = client.getMovieInfoById("NoMuchMovie");
		System.out.println("testGetNoSuchMovieInfoById: "
				+ omdbApiStyleMovie);
		assertNull("No such movie with this Id was ever released",
				omdbApiStyleMovie.getTitle());
	}
}
