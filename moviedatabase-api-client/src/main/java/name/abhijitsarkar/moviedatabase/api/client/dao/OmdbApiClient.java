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

import java.util.Map;

import name.abhijitsarkar.moviedatabase.api.client.domain.Movie;
import name.abhijitsarkar.moviedatabase.api.client.domain.OmdbApiStyleMovie;

/**
 * Concrete OMDB API client instance.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class OmdbApiClient extends AbstractClient {

	public OmdbApiClient(String endpoint) {
		super(endpoint);
	}

	public Movie getMovieInfoByTitleAndYear(String title, short year,
			Map<String, String>... param) {
		Map<String, String> queryParam = getQueryParam(param);
		queryParam.put(getEnv().getProperty("omdbapi.title"), title);
		queryParam
				.put(getEnv().getProperty("omdbapi.year"), Short.toString(year));

		return getMovieInfoInternal(queryParam, OmdbApiStyleMovie.class);
	}

	public Movie getMovieInfoByTitleOnly(String title,
			Map<String, String>... param) {
		Map<String, String> queryParam = getQueryParam(param);
		queryParam.put(getEnv().getProperty("omdbapi.title"), title);

		return getMovieInfoInternal(queryParam, OmdbApiStyleMovie.class);
	}

	public Movie getMovieInfoById(String id, Map<String, String>... param) {
		Map<String, String> queryParam = getQueryParam(param);
		queryParam.put(getEnv().getProperty("omdbapi.id"), id);

		return getMovieInfoInternal(queryParam, OmdbApiStyleMovie.class);
	}
}
