package com.github.abhijitsarkar.moviemanager.ui.eventhandler;

import static com.github.abhijitsarkar.moviemanager.ui.util.UIUtil.createImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.abhijitsarkar.moviemanager.ui.util.Image;

public class TextFieldListener implements DocumentListener {

	JTextField inputDirField;
	JTextField outputFileField;
	JButton createButton;
	JLabel messageLabel;

	public TextFieldListener(JTextField inputDirField,
			JTextField outputFileField, JButton createButton,
			JLabel messageLabel) {
		this.inputDirField = inputDirField;
		this.outputFileField = outputFileField;
		this.createButton = createButton;
		this.messageLabel = messageLabel;
	}

	@Override
	public void changedUpdate(DocumentEvent de) {
		doStuff();
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		doStuff();
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		doStuff();

	}

	private void doStuff() {
		if (inputDirField.getText().isEmpty()) {
			messageLabel.setText("Input directory cannot be empty.");
			messageLabel.setIcon(createImageIcon(Image.WARNING));

			createButton.setEnabled(false);
		} else if (outputFileField.getText().isEmpty()) {
			messageLabel.setText("Output file cannot be empty.");
			messageLabel.setIcon(createImageIcon(Image.WARNING));

			createButton.setEnabled(false);
		} else {
			messageLabel.setText("Create a report.");
			messageLabel.setIcon(null);

			createButton.setEnabled(true);
		}
	}
}
