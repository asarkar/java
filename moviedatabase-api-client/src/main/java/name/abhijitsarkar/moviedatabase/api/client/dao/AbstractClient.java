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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import name.abhijitsarkar.moviedatabase.api.client.domain.Movie;

/**
 * Abstracts common functions like setting query parameters and actually issuing
 * the Http request. Subclasses are free to, but would not normally need to,
 * override these methods.
 * 
 * @author Abhijit Sarkar
 */
@PropertySource("classpath:/com/github/abhijitsarkar/moviedatabase/api/client/config/client.properties")
public abstract class AbstractClient implements Client {
	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	protected AbstractClient(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * The resource endpoint. Set by each subclass.
	 */
	private final String endpoint;
	private static final Logger logger = Logger.getLogger(AbstractClient.class);

	public String getEndpoint() {
		return endpoint;
	}

	public Environment getEnv() {
		return env;
	}

	/**
	 * No-op, subclasses need to override if they want to have their own
	 * validation logic.
	 * 
	 * @param param
	 *            Query parameter Map.
	 */
	protected void validateParam(Map<String, String> param) {
	};

	/**
	 * 
	 * @param param
	 *            Query parameter Map.
	 * @param genericType
	 *            The concrete type that will be used for unmarshal.
	 * @return A movie.
	 */
	protected Movie getMovieInfoInternal(Map<String, String> param,
			Class<? extends Movie> genericType) {
		Assert.notNull(genericType);
		Assert.notEmpty(param);

		return restTemplate.getForObject(createUriTemplate(param), genericType,
				param);
	}

	/**
	 * Creates a URI template by appending the query parameters in the form of
	 * k={k}, where k is a key from the Map.
	 * 
	 * @param queryParams
	 *            Query parameters.
	 * @return Expanded URI template.
	 */
	private String createUriTemplate(Map<String, String> queryParams) {
		final String queryParamsStart = "?";
		final String uriVariableStart = "{";
		final String uriVariableEnd = "}";
		final String uriVariableSeparator = "&";
		final char uriPathSeparator = '/';

		StringBuilder uriTemplate = new StringBuilder(endpoint);

		Set<String> keys = queryParams.keySet();

		/* Check if the Uri template ends with a path separator; if not, add one. */
		if (!(uriTemplate.codePointBefore(uriTemplate.length()) == uriPathSeparator))
			uriTemplate.append(uriPathSeparator);

		uriTemplate.append(queryParamsStart);

		for (String key : keys)
			uriTemplate.append(key).append("=").append(uriVariableStart)
					.append(key).append(uriVariableEnd)
					.append(uriVariableSeparator);

		/* Delete the extra path separator at the end */
		uriTemplate.deleteCharAt((uriTemplate.length() - 1));

		logger.debug("Created URI template: " + uriTemplate);

		return uriTemplate.toString();
	}

	/**
	 * 
	 * @param param
	 *            Query parameter Map as Varargs.
	 * @return Query parameter Map.
	 */
	protected Map<String, String> getQueryParam(Map<String, String>[] param) {
		if (param == null || param.length == 0 || param[0] == null
				|| param[0].isEmpty())
			return new HashMap<String, String>();

		return param[0];
	}
}
