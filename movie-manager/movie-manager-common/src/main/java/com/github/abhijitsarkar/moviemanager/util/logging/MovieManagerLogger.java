package com.github.abhijitsarkar.moviemanager.util.logging;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MovieManagerLogger {
	private static Logger logger = null;
	private static String CONFIG_FILE_PATH = "/log4j.properties";

	private MovieManagerLogger() {
		logger = Logger.getLogger(MovieManagerLogger.class);

		try {
			PropertyConfigurator.configure(getClass().getResource(
					CONFIG_FILE_PATH));
		} catch (Exception fnfe) {
			BasicConfigurator.configure();
		}
	}

	public static Logger getInstance() {
		if (logger == null) {
			new MovieManagerLogger();
		}

		return logger;
	}
}