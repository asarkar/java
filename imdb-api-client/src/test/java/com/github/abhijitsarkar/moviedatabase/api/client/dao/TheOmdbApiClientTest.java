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

package com.github.abhijitsarkar.moviedatabase.api.client.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.abhijitsarkar.moviedatabase.api.client.AbstractContextAwareTest;
import com.github.abhijitsarkar.moviedatabase.api.client.domain.Movie;

/**
 * 
 * @author Abhijit Sarkar
 */
public class TheOmdbApiClientTest extends AbstractContextAwareTest {
	@Autowired
	@Qualifier("theOmdbApiClient")
	private Client client;

	@Before
	public void testAutowire() {
		assertNotNull(client);
	}

	@Test
	public void testGetMovieInfoByTitleAndYear() {
		@SuppressWarnings("unchecked")
		Movie theOmdbApiStyleMovie = client.getMovieInfoByTitleAndYear(
				"Star Trek", (short) 1996);
		assertNotNull("At least one Star Trek movie should've been found",
				theOmdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0117731",
				theOmdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek: First Contact",
				theOmdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 1996, theOmdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", theOmdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", theOmdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", theOmdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", theOmdbApiStyleMovie.getImdbVotes());

		System.out.println("testGetMovieInfoByTitleAndYear: "
				+ theOmdbApiStyleMovie);
	}

	@Test
	public void testGetMovieInfoById() {
		@SuppressWarnings("unchecked")
		Movie theOmdbApiStyleMovie = client.getMovieInfoById("tt0117731");
		assertNotNull("At least one Star Trek movie should've been found",
				theOmdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0117731",
				theOmdbApiStyleMovie.getImdbId());
		assertEquals("Wrong IMDB Id", "tt0117731",
				theOmdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek: First Contact",
				theOmdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 1996, theOmdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", theOmdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", theOmdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", theOmdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", theOmdbApiStyleMovie.getImdbVotes());

		System.out.println("testGetMovieInfoById: " + theOmdbApiStyleMovie);
	}

	@Test
	public void testGetMovieInfoByTitleOnly() {
		@SuppressWarnings("unchecked")
		Movie theOmdbApiStyleMovie = client
				.getMovieInfoByTitleOnly("Star Trek");
		assertNotNull("At least one Star Trek movie should've been found",
				theOmdbApiStyleMovie);
		assertEquals("Wrong IMDB Id", "tt0796366",
				theOmdbApiStyleMovie.getImdbId());
		assertEquals("Wrong title", "Star Trek",
				theOmdbApiStyleMovie.getTitle());
		assertEquals("Wrong release year", 2009, theOmdbApiStyleMovie.getYear());
		assertNotNull("Wrong genre", theOmdbApiStyleMovie.getGenre());
		assertNotNull("Wrong rating", theOmdbApiStyleMovie.getImdbRating());
		assertNotNull("Wrong runtime", theOmdbApiStyleMovie.getRuntime());
		assertNotNull("Wrong votes", theOmdbApiStyleMovie.getImdbVotes());
		System.out.println("testGetMovieInfoByTitleOnly: "
				+ theOmdbApiStyleMovie);
	}

	@Test
	public void testGetNoSuchMovieInfoById() {
		@SuppressWarnings("unchecked")
		Movie theOmdbApiStyleMovie = client.getMovieInfoById("NoMuchMovie");
		System.out.println("testGetNoSuchMovieInfoById: "
				+ theOmdbApiStyleMovie);
		assertNull("No such movie with this Id was ever released",
				theOmdbApiStyleMovie.getTitle());
	}
}
