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

import java.util.Map;

import com.github.abhijitsarkar.moviedatabase.api.client.domain.Movie;

/**
 * All clients must use this interface for making API calls.
 * 
 * @author Abhijit Sarkar
 */
public interface Client {
	/**
	 * 
	 * @param title
	 *            Movie title.
	 * @param year
	 *            Movie release year.
	 * @param param
	 *            Optional query parameters that the API supports.
	 * @return A movie.
	 */
	public abstract Movie getMovieInfoByTitleAndYear(String title, short year,
			Map<String, String>... param);

	/**
	 * 
	 * @param title
	 *            Movie title.
	 * @param param
	 *            Optional query parameters that the API supports.
	 * @return A movie.
	 */
	public abstract Movie getMovieInfoByTitleOnly(String title,
			Map<String, String>... param);

	/**
	 * 
	 * @param id
	 *            IMDB id.
	 * @param param
	 *            Optional query parameters that the API supports.
	 * @return A movie.
	 */
	public abstract Movie getMovieInfoById(String id,
			Map<String, String>... param);

	public abstract String getEndpoint();
}
