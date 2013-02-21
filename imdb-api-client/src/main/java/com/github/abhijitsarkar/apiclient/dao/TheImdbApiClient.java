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

import java.util.Map;

import com.github.abhijitsarkar.apiclient.domain.Movie;
import com.github.abhijitsarkar.apiclient.domain.TheImdbApiStyleMovie;

/**
 * Concrete IMDB API client instance.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class TheImdbApiClient extends AbstractClient {

    public TheImdbApiClient(String endpoint) {
	super(endpoint);
    }

    public Movie getMovieInfoByTitleAndYear(String title, short year,
	    Map<String, String>... param) {
	Map<String, String> queryParam = getQueryParam(param);
	queryParam.put(env.getProperty("theimdbapi.title"), title);
	queryParam
		.put(env.getProperty("theimdbapi.year"), Short.toString(year));

	return getMovieInfoInternal(queryParam, TheImdbApiStyleMovie.class);
    }

    public Movie getMovieInfoByTitleOnly(String title,
	    Map<String, String>... param) {
	Map<String, String> queryParam = getQueryParam(param);
	queryParam.put(env.getProperty("theimdbapi.title"), title);

	return getMovieInfoInternal(queryParam, TheImdbApiStyleMovie.class);
    }

    public Movie getMovieInfoById(String id, Map<String, String>... param) {
	Map<String, String> queryParam = getQueryParam(param);
	queryParam.put(env.getProperty("theimdbapi.id"), id);

	return getMovieInfoInternal(queryParam, TheImdbApiStyleMovie.class);
    }
}
