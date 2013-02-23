package com.github.abhijitsarkar.moviemanager.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;

public interface MovieService {
	public abstract Set<Movie> getMovieSet(String path) throws IOException;

	public abstract Set<Movie> filterMovieSetByGenre(Set<Movie> movieSet,
			String genre);

	public abstract Map<String, Set<Movie>> groupMovieSetByGenre(
			Set<Movie> movieSet);

	public abstract GenreSummary getSummaryByGenre(
			Set<Movie> movieSetFilteredByGenre, String genre);

	public abstract Summary getSummary(
			Map<String, Set<Movie>> movieSetGroupedByGenre);

	public String getMovieDirectory();
}
