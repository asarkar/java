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

package com.github.abhijitsarkar.moviedatabase.api.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.github.abhijitsarkar.moviedatabase.api.client.AbstractContextAwareTest;
import com.github.abhijitsarkar.moviedatabase.api.client.dao.Client;
import com.github.abhijitsarkar.moviedatabase.api.client.dao.DeanClatworthyClient;
import com.github.abhijitsarkar.moviedatabase.api.client.dao.OmdbApiClient;

/**
 * 
 * @author Abhijit Sarkar
 * 
 */
public class ClientConfigTest extends AbstractContextAwareTest {

	@Autowired
	@Qualifier("omdbApiClient")
	private Client omdbApiClient;

	@Autowired
	@Qualifier("deanClatworthyClient")
	private Client deanClatworthyClient;

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testOmdbApiClient() {
		assertNotNull(omdbApiClient);
		assertTrue(omdbApiClient instanceof OmdbApiClient);
		assertEquals("http://www.omdbapi.com", omdbApiClient.getEndpoint());
	}

	@Test
	public void testDeanClatworthyClient() {
		assertNotNull(deanClatworthyClient);
		assertTrue(deanClatworthyClient instanceof DeanClatworthyClient);
		assertEquals("http://deanclatworthy.com/imdb",
				deanClatworthyClient.getEndpoint());
	}

	@Test
	public void testEnvironment() {
		assertNotNull(env);
		assertTrue(env.containsProperty("omdbapi.endpoint"));
		assertTrue(env.containsProperty("omdbapi.id"));
		assertTrue(env.containsProperty("omdbapi.title"));
		assertTrue(env.containsProperty("omdbapi.year"));
		assertTrue(env.containsProperty("omdbapi.mediatype"));
		assertTrue(env.containsProperty("omdbapi.plot"));

		assertTrue(env.containsProperty("deanclatworthyapi.endpoint"));
		assertTrue(env.containsProperty("deanclatworthyapi.title"));
		assertTrue(env.containsProperty("deanclatworthyapi.year"));
		assertTrue(env.containsProperty("deanclatworthyapi.mediatype"));
		assertTrue(env.containsProperty("deanclatworthyapi.yearguesing"));
		assertTrue(env.containsProperty("connect.timeout"));
		assertTrue(env.containsProperty("read.timeout"));
	}

	@Test
	public void testRestTemplate() {
		List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		assertNotNull(messageConverters);
		assertTrue(messageConverters.size() == 1);
		HttpMessageConverter<?> messageConverter = messageConverters.get(0);
		assertNotNull(messageConverter);
		assertTrue(messageConverter instanceof MappingJacksonHttpMessageConverter);
		List<MediaType> mediaTypes = messageConverter.getSupportedMediaTypes();
		assertNotNull(mediaTypes);
		assertTrue(mediaTypes.contains(MediaType.TEXT_HTML));
		assertTrue(mediaTypes.contains(MediaType.APPLICATION_JSON));

		ClientHttpRequestFactory requestFactory = restTemplate
				.getRequestFactory();
		assertNotNull(requestFactory);
	}
}
