/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.abhijitsarkar.moviemanager.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;

import javax.swing.border.AbstractBorder;

/**
 * 
 * @author abhijit
 */
public class DashedLineBorder extends AbstractBorder {

	private static final long serialVersionUID = 4227570824244866387L;
	private static final float dashes[] = { 2 }; // specify dash length
	private static Stroke dashedStroke;
	private Color border;
	private int thickness;

	public DashedLineBorder(Color border, int thickness) {
		this.border = border;
		this.thickness = thickness;

		dashedStroke = new BasicStroke(this.thickness, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_MITER, 10, dashes, 0);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(border);
		g2.setStroke(dashedStroke);
		g2.drawRect(x, y, width - 2, height - 2);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(2, 2, 2, 2);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}
}
