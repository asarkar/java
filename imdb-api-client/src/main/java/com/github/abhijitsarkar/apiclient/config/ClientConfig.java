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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.github.abhijitsarkar.apiclient.dao.Client;
import com.github.abhijitsarkar.apiclient.dao.DeanClatworthyClient;
import com.github.abhijitsarkar.apiclient.dao.TheImdbApiClient;
import com.github.abhijitsarkar.apiclient.http.HttpRequestInterceptor;

/**
 * Declares the Beans may be processed by the Spring container to generate bean
 * definitions and service requests for those beans at runtime.
 * 
 * @author Abhijit Sarkar
 * 
 */
@Configuration
@PropertySource("classpath:/com/github/abhijitsarkar/apiclient/config/client.properties")
public abstract class ClientConfig {
    @Autowired
    private Environment env;

    @Bean
    Client theImdbApiClient() {
	return new TheImdbApiClient(env.getProperty("theimdbapi.endpoint"));
    }

    @Bean
    Client deanClatworthyClient() {
	return new DeanClatworthyClient(
		env.getProperty("deanclatworthyapi.endpoint"));
    }

    /*
     * RestTemplate with Interceptor.
     */
    @Bean
    RestTemplate restTemplate() {
	RestTemplate restTemplate = new RestTemplate(clientRequestFactory());
	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
	messageConverters.add(jsonMessageConverter());
	restTemplate.setMessageConverters(messageConverters);
	List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
	interceptors.add(new HttpRequestInterceptor());
	restTemplate.setInterceptors(interceptors);
	return restTemplate;
    }

    /*
     * Buffer the request and response so that the response can be read multiple
     * times, once for logging and then for unmarshaling.
     */
    private ClientHttpRequestFactory clientRequestFactory() {
	SimpleClientHttpRequestFactory clientRequestFactory = new SimpleClientHttpRequestFactory();
	clientRequestFactory.setConnectTimeout(env.getProperty(
		"connect.timeout", int.class, 15000));
	clientRequestFactory.setReadTimeout(env.getProperty("read.timeout",
		int.class, 15000));
	return new BufferingClientHttpRequestFactory(clientRequestFactory);
    }

    /*
     * Set up the HttpMessageConverter to handle text/html type. This wouldn't
     * be necessary of the server returned the correct media type.
     */
    private HttpMessageConverter<Object> jsonMessageConverter() {
	AbstractHttpMessageConverter<Object> converter = new MappingJacksonHttpMessageConverter();
	List<MediaType> mediaTypes = new ArrayList<MediaType>();
	mediaTypes.add(MediaType.TEXT_HTML);
	mediaTypes.add(MediaType.APPLICATION_JSON);
	converter.setSupportedMediaTypes(mediaTypes);

	return converter;
    }
}
