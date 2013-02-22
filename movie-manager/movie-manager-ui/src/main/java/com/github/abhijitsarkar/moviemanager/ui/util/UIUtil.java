/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager.ui.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.github.abhijitsarkar.moviemanager.ui.MainFrame;
import com.github.abhijitsarkar.moviemanager.util.logging.MovieManagerLogger;

/**
 * 
 * @author abhijit
 */
public class UIUtil {

	private static Logger logger = MovieManagerLogger.getInstance();
	private static URL SUCCESS_SOUND = UIUtil.class.getResource(Sound.SUCCESS
			.toString());
	private static URL ERROR_SOUND = UIUtil.class.getResource(Sound.ERROR
			.toString());

	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(Image image) {
		java.net.URL imgURL = MainFrame.class.getResource(image.toString());
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			logger.error("Couldn't find image file: " + image);
			return null;
		}
	}

	public static void cleanup(boolean success, JButton button, JLabel label,
			String message, String successResult, Exception... onlyWhenError) {
		if (message == null || message.isEmpty()) {
			message = "Failed to complete the requested task.";
		}

		if (success) {
			label.setIcon(createImageIcon(Image.SUCCESS));

			playSound(Sound.SUCCESS);
		} else {
			if (onlyWhenError.length > 0) {
				logger.error(message, onlyWhenError[0]);
			}

			label.setIcon(createImageIcon(Image.ERROR));

			playSound(Sound.ERROR);
		}

		button.setEnabled(success);
		label.setText(message);
	}

	public static String getStacktraceAsString(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static void playSound(Sound sound) {
		Clip clip = null;
		AudioInputStream ais = null;
		URL clipURL = null;

		try {
			clip = AudioSystem.getClip();

			switch (sound) {
			case SUCCESS:
				clipURL = SUCCESS_SOUND;
				break;
			default:
				clipURL = ERROR_SOUND;
				break;
			}

			ais = AudioSystem.getAudioInputStream(clipURL);

			// if (clip.isActive()) {
			// return;
			// }

			clip.open(ais);
			clip.start();
			sleepForSomeTime(250L);
		} catch (Exception ex) {
			logger.error("Failed to play the audio file: " + sound.toString(),
					ex);
		} finally {
			clip.stop();
			clip.close();
			try {
				ais.close();
			} catch (IOException ex) {
				logger.error(
						"Failed while attempting to close the audio stream.",
						ex);
			}
		}
	}

	public static void sleepForSomeTime(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ignore) {
		}
	}
}
