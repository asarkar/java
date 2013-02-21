/* Copyright (c) 2012,the original author or authors.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see http://www.gnu.org/licenses. */

package com.github.abhijitsarkar.apiclient.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.abhijitsarkar.apiclient.AbstractContextAwareTest;
import com.github.abhijitsarkar.apiclient.dao.Client;
import com.github.abhijitsarkar.apiclient.domain.Movie;

/**
 * 
 * @author Abhijit Sarkar
 */
public class TheImdbApiClientTest extends AbstractContextAwareTest {
    @Autowired
    @Qualifier("theImdbApiClient")
    private Client client;
    
    @Before
    public void testAutowire() {
	assertNotNull(client);
    }

    @Test
    public void testGetMovieInfoByTitleAndYear() {
	@SuppressWarnings("unchecked")
	Movie theImdbApiStyleMovie = client.getMovieInfoByTitleAndYear(
		"Star Trek", (short) 1996);
	assertNotNull("At least one Star Trek movie should've been found",
		theImdbApiStyleMovie);
	assertEquals("Wrong IMDB Id", "tt0117731",
		theImdbApiStyleMovie.getImdbId());
	assertEquals("Wrong release year", 1996, theImdbApiStyleMovie.getYear());

	System.out.println("testGetMovieInfoByTitleAndYear: "
		+ theImdbApiStyleMovie);
    }

    @Test
    public void testGetMovieInfoById() {
	@SuppressWarnings("unchecked")
	Movie theImdbApiStyleMovie = client.getMovieInfoById("tt0117731");
	assertNotNull("At least one Star Trek movie should've been found",
		theImdbApiStyleMovie);
	assertEquals("Wrong IMDB Id", "tt0117731",
		theImdbApiStyleMovie.getImdbId());
	assertEquals("Wrong release year", 1996, theImdbApiStyleMovie.getYear());

	System.out.println("testGetMovieInfoById: " + theImdbApiStyleMovie);
    }

    @Test
    public void testGetMovieInfoByTitleOnly() {
	@SuppressWarnings("unchecked")
	Movie theImdbApiStyleMovie = client
		.getMovieInfoByTitleOnly("Star Trek");
	assertNotNull("At least one Star Trek movie should've been found",
		theImdbApiStyleMovie);
	assertEquals("Wrong IMDB Id", "tt0079945",
		theImdbApiStyleMovie.getImdbId());
	assertEquals("Wrong release year", 1979, theImdbApiStyleMovie.getYear());
	System.out.println("testGetMovieInfoByTitleOnly: "
		+ theImdbApiStyleMovie);
    }

    @Test
    public void testGetNoSuchMovieInfoById() {
	@SuppressWarnings("unchecked")
	Movie theImdbApiStyleMovie = client.getMovieInfoById("NoMuchMovie");
	System.out.println("testGetNoSuchMovieInfoById: "
		+ theImdbApiStyleMovie);
	assertNull("No such movie with this Id was ever released",
		theImdbApiStyleMovie.getTitle());
    }
}
