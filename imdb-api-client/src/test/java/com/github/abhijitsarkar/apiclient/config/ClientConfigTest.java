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

package com.github.abhijitsarkar.apiclient.config;

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

import com.github.abhijitsarkar.apiclient.AbstractContextAwareTest;
import com.github.abhijitsarkar.apiclient.dao.Client;
import com.github.abhijitsarkar.apiclient.dao.DeanClatworthyClient;
import com.github.abhijitsarkar.apiclient.dao.TheImdbApiClient;

/**
 * 
 * @author Abhijit Sarkar
 * 
 */
public class ClientConfigTest extends AbstractContextAwareTest {

    @Autowired
    @Qualifier("theImdbApiClient")
    private Client theImdbApiClient;

    @Autowired
    @Qualifier("deanClatworthyClient")
    private Client deanClatworthyClient;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testTheImdbApiClient() {
	assertNotNull(theImdbApiClient);
	assertTrue(theImdbApiClient instanceof TheImdbApiClient);
	assertEquals("http://www.imdbapi.com", theImdbApiClient.getEndpoint());
    }

    @Test
    public void testDeanClatworthyClient() {
	assertNotNull(deanClatworthyClient);
	assertTrue(deanClatworthyClient instanceof DeanClatworthyClient);
	assertEquals("http://www.deanclatworthy.com/imdb",
		deanClatworthyClient.getEndpoint());
    }

    @Test
    public void testEnvironment() {
	assertNotNull(env);
	assertTrue(env.containsProperty("theimdbapi.endpoint"));
	assertTrue(env.containsProperty("theimdbapi.id"));
	assertTrue(env.containsProperty("theimdbapi.title"));
	assertTrue(env.containsProperty("theimdbapi.year"));
	assertTrue(env.containsProperty("theimdbapi.mediatype"));
	assertTrue(env.containsProperty("theimdbapi.plot"));
	
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
