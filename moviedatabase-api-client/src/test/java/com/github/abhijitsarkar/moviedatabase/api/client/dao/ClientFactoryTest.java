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

package com.github.abhijitsarkar.moviedatabase.api.client.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.abhijitsarkar.moviedatabase.api.client.ClientType;

/**
 * 
 * @author Abhijit Sarkar
 * 
 */
public class ClientFactoryTest {
	private static ClientFactory factory;

	@BeforeClass
	public static void beforeClassSetUp() {
		factory = ClientFactory.getInstance();
	}

	@AfterClass
	public static void afterClassTearDown() {
		factory = null;
	}

	@Test
	public void testFactory() {
		assertNotNull(factory);
		assertSame(factory, ClientFactory.getInstance()); // test Singleton
	}

	@Test
	public void testClients() {
		Client omdbApiClient = factory.getClient(ClientType.THE_OMDB_API);
		Client deanClatworthyClient = factory
				.getClient(ClientType.DEAN_CLATWORTHY);
		assertNotNull(omdbApiClient);
		assertTrue(omdbApiClient instanceof OmdbApiClient);
		assertNotNull(deanClatworthyClient);
		assertTrue(deanClatworthyClient instanceof DeanClatworthyClient);
		assertEquals("http://www.omdbapi.com", omdbApiClient.getEndpoint());
		assertEquals("http://deanclatworthy.com/imdb",
				deanClatworthyClient.getEndpoint());
	}
}
