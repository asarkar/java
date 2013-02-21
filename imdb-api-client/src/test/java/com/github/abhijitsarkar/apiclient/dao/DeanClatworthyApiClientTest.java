/* Copyright (c) 2012, the original author or authors.

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
import com.github.abhijitsarkar.apiclient.ClientException;
import com.github.abhijitsarkar.apiclient.dao.Client;
import com.github.abhijitsarkar.apiclient.domain.Movie;

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
	assertEquals("Wrong release year", 1996,
		deanClatworthyApiStyleMovie.getYear());

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
