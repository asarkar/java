package com.github.abhijitsarkar.moviemanager.service;

import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;

public interface MovieService {
	public abstract SortedSet<Movie> getMovieSet(String path)
			throws IOException;

	public abstract SortedSet<Movie> filterMovieSetByGenre(
			SortedSet<Movie> movieSet, Genre genre);

	public abstract Map<Genre, SortedSet<Movie>> groupMovieSetByGenre(
			SortedSet<Movie> movieSet);

	public abstract GenreSummary getSummaryByGenre(
			SortedSet<Movie> movieSetFilteredByGenre, Genre genre);

	public abstract Summary getSummary(
			Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre);

	public String getMovieDirectory();
}
