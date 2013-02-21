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

package com.github.abhijitsarkar.apiclient.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * An abstraction for the clients. All concrete movie instances must extend this
 * class.
 * 
 * @author Abhijit Sarkar
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class Movie {

    protected String title;
    protected short year;
    protected String genre;
    protected String mpaaRating;
    protected String runtime;
    protected String imdbRating;
    protected String imdbVotes;
    protected String imdbId;

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract short getYear();

    public abstract void setYear(short year);

    public abstract String getGenre();

    public abstract void setGenre(String genre);

    public abstract String getMpaaRating();

    public abstract void setMpaaRating(String mpaaRating);

    public abstract String getRuntime();

    public abstract void setRuntime(String runtime);

    public abstract String getImdbRating();

    public abstract void setImdbRating(String imdbRating);

    public abstract String getImdbVotes();

    public abstract void setImdbVotes(String imdbVotes);

    public abstract String getImdbId();

    public abstract void setImdbId(String imdbId);
}
