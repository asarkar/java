/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author abhijit
 */
public class ConfigManager {
	/* User settings */
	public static final String USER_SETTINGS_IO_KEY = "user.settings.inputOutput";
	public static final String USER_SETTINGS_IO_START_WITH_KEY = "user.settings.inputOutput.startWith";
	public static final String USER_SETTINGS_IO_LAST_INPUT_DIR_KEY = "user.settings.inputOutput.inputDir";
	public static final String USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY = "user.settings.inputOutput.outputDir";
	public static final String USER_SETTINGS_IO_NO_PREF_VALUE = "noPreference";
	public static final String USER_SETTINGS_IO_START_WITH_VALUE = "startWith";
	public static final String USER_SETTINGS_IO_RMBR_LAST_VALUE = "rememberLast";
	public static final String GENRE_LIST_KEY = "genre.list";
	public static final String GENRE_LIST_VALUE = "Action and Adventure,Animation,"
			+ "Comedy,Documentary,Drama,Horror,Romance,R-Rated Mainstream Movies,"
			+ "Sci-Fi,Thriller,X-Rated,Unknown";
	public static final String GENRE_SEPARATOR = ",";

	/* System internal settings */
	private static final String BASE_DIR = ".moviemanager";
	private static final File CONFIG_DIR = new File(
			System.getProperty("user.home") + File.separator + BASE_DIR
					+ File.separator + "configuration");
	private static final File CONFIG_FILE = new File(CONFIG_DIR, "config.ini");

	/* Not Thread safe!!! */
	private static Properties prop = null;

	static {
		try {
			if (!CONFIG_FILE.exists()) {
				CONFIG_DIR.mkdirs();
				CONFIG_FILE.createNewFile();
			}

			prop = new Properties();
			prop.load(new FileReader(CONFIG_FILE));

			if (getUserSettingsIO() == null) {
				prop.put(ConfigManager.USER_SETTINGS_IO_KEY,
						ConfigManager.USER_SETTINGS_IO_RMBR_LAST_VALUE);

			}
			if (prop.getProperty(GENRE_LIST_KEY) == null) {
				prop.put(GENRE_LIST_KEY, GENRE_LIST_VALUE);
			}
			flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void flush() throws IOException {
		if (CONFIG_FILE.canWrite()) {
			prop.store(new FileWriter(CONFIG_FILE), null);
		} else {
			throw new IOException("Permission denied: can't write to "
					+ CONFIG_FILE.getAbsolutePath());
		}
	}

	public static String getLastOpenedInputDir() throws IOException {
		String lastOpenedInputDir = null;

		String userSettingsIO = prop.getProperty(USER_SETTINGS_IO_KEY);

		if (USER_SETTINGS_IO_START_WITH_VALUE.equals(userSettingsIO)) {
			lastOpenedInputDir = prop
					.getProperty(USER_SETTINGS_IO_START_WITH_KEY);
		} else if (USER_SETTINGS_IO_RMBR_LAST_VALUE.equals(userSettingsIO)) {
			lastOpenedInputDir = prop
					.getProperty(USER_SETTINGS_IO_LAST_INPUT_DIR_KEY);
		}

		if (!USER_SETTINGS_IO_NO_PREF_VALUE.equals(userSettingsIO)
				&& (lastOpenedInputDir == null || !new File(lastOpenedInputDir)
						.exists())) {
			throw new IOException(
					"Could not retrieve last opened input directory.");
		}

		return lastOpenedInputDir;
	}

	public static void setLastOpenedInputDir(String lastOpenedInputDir) {
		prop.put(USER_SETTINGS_IO_LAST_INPUT_DIR_KEY, lastOpenedInputDir);
	}

	public static String getLastOpenedOutputDir() throws IOException {
		String lastOpenedOutputDir = null;

		String userSettingsIO = prop.getProperty(USER_SETTINGS_IO_KEY);

		if (USER_SETTINGS_IO_START_WITH_VALUE.equals(userSettingsIO)) {
			lastOpenedOutputDir = prop
					.getProperty(USER_SETTINGS_IO_START_WITH_KEY);
		} else if (USER_SETTINGS_IO_RMBR_LAST_VALUE.equals(userSettingsIO)) {
			lastOpenedOutputDir = prop
					.getProperty(USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY);
		}

		if (!USER_SETTINGS_IO_NO_PREF_VALUE.equals(userSettingsIO)
				&& (lastOpenedOutputDir == null || !new File(
						lastOpenedOutputDir).exists())) {
			throw new IOException(
					"Could not retrieve last opened output directory.");
		}

		return lastOpenedOutputDir;
	}

	public static void setLastOpenedOutputDir(String lastOpenedOutputDir) {
		prop.put(USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY, lastOpenedOutputDir);
	}

	public static List<String> getGenreList() throws IOException {
		String genres = prop.getProperty(GENRE_LIST_KEY);

		if (!genres.isEmpty()) {
			return Arrays.asList(genres.split(GENRE_SEPARATOR));
		}
		throw new IOException("Could not retrieve genre list.");
	}

	public static String getUserSettingsIO() {
		return prop.getProperty(USER_SETTINGS_IO_KEY);
	}

	public static void setUserSettingsIO(String value) {
		prop.put(USER_SETTINGS_IO_KEY, value);
	}

	public static String getUserSettingsIOStartWith() {
		return prop.getProperty(USER_SETTINGS_IO_START_WITH_KEY);
	}

	public static void setUserSettingsIOStartWith(String value) {
		prop.put(USER_SETTINGS_IO_START_WITH_KEY, value);
	}
}
