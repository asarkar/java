/* Copyright (c) 2013, the original author or authors.

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
import name.abhijitsarkar.moviedatabase.api.client.ClientException;
import name.abhijitsarkar.moviedatabase.api.client.dao.Client;
import name.abhijitsarkar.moviedatabase.api.client.domain.Movie;

/**
 * 
 * @author Abhijit Sarkar
 */
public class DeanClatworthyApiClientTest extends AbstractContextAwareTest {

	@Autowired
	@Qualifier("deanClatworthyClient")
	private Client client;

	@Before
	public void testAutowire() {
		assertNotNull(client);
	}

	@Test
	public void testGetMovieInfoByTitleAndYear() {
		@SuppressWarnings("unchecked")
		Movie deanClatworthyApiStyleMovie = client.getMovieInfoByTitleAndYear(
				"Star Trek", (short) 1996);
		assertNotNull("At least one Star Trek movie should've been found",
				deanClatworthyApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0117731",
				deanClatworthyApiStyleMovie.getImdbId());
		 assertEquals("Wrong title", "Star Trek: First Contact",
		 deanClatworthyApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 1996,
				deanClatworthyApiStyleMovie.getYear());
		assertNotNull("Wrong genre", deanClatworthyApiStyleMovie.getGenre());
		assertNotNull("Wrong rating",
				deanClatworthyApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", deanClatworthyApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", deanClatworthyApiStyleMovie.getImdbVotes());

		System.out.println("testGetMovieInfoByTitleAndYear: "
				+ deanClatworthyApiStyleMovie);
	}

	@Test
	public void testGetMovieInfoByTitleOnly() {
		@SuppressWarnings("unchecked")
		Movie deanClatworthyApiStyleMovie = client
				.getMovieInfoByTitleOnly("Star Trek");
		assertNotNull("At least one Star Trek movie should've been found",
				deanClatworthyApiStyleMovie);
		assertEquals("Wrong title", "Star Trek Into Darkness",
				deanClatworthyApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 2013,
				deanClatworthyApiStyleMovie.getYear());
		assertNotNull("Wrong genre", deanClatworthyApiStyleMovie.getGenre());
		assertNotNull("Wrong rating",
				deanClatworthyApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", deanClatworthyApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", deanClatworthyApiStyleMovie.getImdbVotes());
		System.out.println("testGetMovieInfoByTitleOnly: "
				+ deanClatworthyApiStyleMovie);
	}

	@Test
	public void testGetNoSuchMovieInfoByTitle() {
		@SuppressWarnings("unchecked")
		Movie deanClatworthyApiStyleMovie = client
				.getMovieInfoByTitleOnly("NoMuchMovie");
		System.out.println("testGetNoSuchMovieInfoByTitle: "
				+ deanClatworthyApiStyleMovie);
		assertNull("No such movie with this Id was ever released",
				deanClatworthyApiStyleMovie.getTitle());
	}

	@SuppressWarnings("unchecked")
	@Test(expected = ClientException.class)
	public void testGetMovieInfoById() {
		client.getMovieInfoById("tt0117731");
	}
}
