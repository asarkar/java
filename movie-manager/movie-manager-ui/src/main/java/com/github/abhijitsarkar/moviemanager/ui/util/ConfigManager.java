/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager.ui.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 
 * @author abhijit
 */
public class ConfigManager {

	public static final String USER_SETTINGS_IO_KEY = "user.settings.inputOutput";
	public static final String USER_SETTINGS_IO_START_WITH_KEY = "user.settings.inputOutput.startWith";
	public static final String USER_SETTINGS_IO_LAST_INPUT_DIR_KEY = "user.settings.inputOutput.inputDir";
	public static final String USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY = "user.settings.inputOutput.outputDir";
	public static final String USER_SETTINGS_IO_NO_PREF_VALUE = "noPreference";
	public static final String USER_SETTINGS_IO_START_WITH_VALUE = "startWith";
	public static final String USER_SETTINGS_IO_RMBR_LAST_VALUE = "rememberLast";
	private static final String BASE_DIR = ".moviemanager";
	private static final File CONFIG_DIR = new File(
			System.getProperty("user.home") + File.separator + BASE_DIR
					+ File.separator + "configuration");
	private static final File CONFIG_FILE = new File(CONFIG_DIR, "config.ini");
	// private static final String osName;
	// private static final String WINDOWS_OS = "WINDOWS";
	// private static final String UNIX_OS = "UNIX";

	static {
		try {
			if (!CONFIG_FILE.exists()) {
				CONFIG_DIR.mkdirs();
				CONFIG_FILE.createNewFile();
			}

			if (getUserSetting() == null) {
				Properties prop = getConfigProperties();
				prop.put(ConfigManager.USER_SETTINGS_IO_KEY,
						ConfigManager.USER_SETTINGS_IO_RMBR_LAST_VALUE);
				setConfigProperties(prop);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		// {
		// osName = WINDOWS_OS;
		// } else {
		// osName = UNIX_OS;
		// }
	}

	public static Properties getConfigProperties()
			throws FileNotFoundException, IOException {
		if (CONFIG_FILE.canRead()) {
			Properties prop = new Properties();
			prop.load(new FileReader(CONFIG_FILE));

			return prop;
		}

		throw new IOException("Permission denied: can't read from "
				+ CONFIG_FILE.getAbsolutePath());
	}

	public static void setConfigProperties(Properties prop) throws IOException {
		if (CONFIG_FILE.canWrite()) {
			prop.store(new FileWriter(CONFIG_FILE), null);
		} else {
			throw new IOException("Permission denied: can't write to "
					+ CONFIG_FILE.getAbsolutePath());
		}
	}

	public static String getLastOpenedInputDir() {
		String lastOpenedInputDir = null;

		try {
			Properties prop = getConfigProperties();
			String userSettingsIO = prop.getProperty(USER_SETTINGS_IO_KEY);

			if (USER_SETTINGS_IO_START_WITH_VALUE.equals(userSettingsIO)) {
				lastOpenedInputDir = prop
						.getProperty(USER_SETTINGS_IO_START_WITH_KEY);
			} else if (USER_SETTINGS_IO_RMBR_LAST_VALUE.equals(userSettingsIO)) {
				lastOpenedInputDir = prop
						.getProperty(USER_SETTINGS_IO_LAST_INPUT_DIR_KEY);
			}

			if (lastOpenedInputDir != null
					&& !new File(lastOpenedInputDir).exists()) {
				throw new IOException(
						"Last opened input directory does not exist. "
								+ lastOpenedInputDir);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not retrieve last opened input directory.",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}

		return lastOpenedInputDir;
	}

	public static void setLastOpenedInputDir(String lastOpenedInputDir) {
		try {
			Properties prop = getConfigProperties();
			prop.put(USER_SETTINGS_IO_LAST_INPUT_DIR_KEY, lastOpenedInputDir);

			setConfigProperties(prop);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not save as last opened input directory.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String getLastOpenedOutputDir() {
		String lastOpenedOutputDir = null;

		try {
			Properties prop = getConfigProperties();
			String userSettingsIO = prop.getProperty(USER_SETTINGS_IO_KEY);

			if (USER_SETTINGS_IO_START_WITH_VALUE.equals(userSettingsIO)) {
				lastOpenedOutputDir = prop
						.getProperty(USER_SETTINGS_IO_START_WITH_KEY);
			} else if (USER_SETTINGS_IO_RMBR_LAST_VALUE.equals(userSettingsIO)) {
				lastOpenedOutputDir = prop
						.getProperty(USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY);
			}

			if (lastOpenedOutputDir != null
					&& !new File(lastOpenedOutputDir).exists()) {
				throw new IOException(
						"Last opened output directory does not exist. "
								+ lastOpenedOutputDir);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not retrieve last opened output directory.",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}

		return lastOpenedOutputDir;
	}

	public static void setLastOpenedOutputDir(String lastOpenedOutputDir) {
		try {
			Properties prop = getConfigProperties();
			prop.put(USER_SETTINGS_IO_LAST_OUTPUT_DIR_KEY, lastOpenedOutputDir);

			setConfigProperties(prop);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not save as last opened output directory.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String getUserSetting() {

		Properties prop = null;
		try {
			prop = getConfigProperties();
			return prop.getProperty(USER_SETTINGS_IO_KEY);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not retrieve user settings.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	// private static String pathConvert(String path) {
	// if (WINDOWS_OS.equals(osName) && path != null) {
	// return path.replace(File.separator, File.separator + File.separator);
	// }
	//
	// return path;
	// }
}
