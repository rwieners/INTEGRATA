package de.integrata.hkw07.gui;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

/*
 * 
 */
public class GraphDisplay extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	private ArrayList<Integer> data = new ArrayList<>();;
	private int w;
	private int h;
	private Graphics2D g2;
	final int PAD = 20;

	
	
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		w = getWidth();
		h = getHeight();
		// Draw ordinate.
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
		// Draw abcissa.
		g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
		// Draw labels.
		Font font = g2.getFont();
		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();
		// Ordinate label.
		String s = "Ist-Temperatur";
		float sy = PAD + ((h - 2 * PAD) - s.length() * sh) / 2 + lm.getAscent();
		for (int i = 0; i < s.length(); i++) {
			String letter = String.valueOf(s.charAt(i));
			float sw = (float) font.getStringBounds(letter, frc).getWidth();
			float sx = (PAD - sw) / 2;
			g2.drawString(letter, sx, sy);
			sy += sh;
		}
		
		// Abcissa label.
		s = "Zeit";
		sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
		float sw = (float) font.getStringBounds(s, frc).getWidth();
		float sx = (w - sw) / 2;
		g2.drawString(s, sx, sy);
		this.drawLines();
	}
	
	/**
	 * 
	 */
	private void drawLines() {
		// Draw lines.
		double xInc = (double) (w - 2 * PAD) / (data.size() - 1);
		double scale = (double) (h - 2 * PAD) / getMax();
		g2.setPaint(Color.green.darker());
		for (int i = 0; i < data.size() - 1; i++) {
			double x1 = PAD + i * xInc;
			double y1 = h - PAD - scale * data.get(i);
			double x2 = PAD + (i + 1) * xInc;
			double y2 = h - PAD - scale * data.get(i + 1);
			g2.draw(new Line2D.Double(x1, y1, x2, y2));
		}
		// Mark data points.
		if (xInc > 2000) xInc = 0;
		g2.setPaint(Color.red);
		for (int i = 0; i < data.size(); i++) {
			double x = PAD + i * xInc;
			double y = h - PAD - scale * data.get(i);
			g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
		}
	}

	/**
	 * Calculate the Maximum of a given List of data values.
	 * For HKW: Fix 130
	 * 
	 * @return maximum y-axis
	 */
	private int getMax() {
		/*
		int max = -Integer.MAX_VALUE;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) > max)
				max = data.get(i);
		}
		*/
		int max = 130;
		return max;
	}

	@Override
	public void update(Observable arg0, Object temp) {
		System.out.println("GraphDisplay update: temp=" + temp + " from " + arg0);
		data.add((Integer)temp);
		drawLines();

		this.revalidate();
		this.repaint();
		//this.getParent().repaint();
	}

	
	public static void main(String[] args) {
		new GraphDisplay();
	}

	public GraphDisplay() {
		JFrame f = new JFrame("HKW GraphDisplay");
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setSize(800, 400);
		f.setLocation(100, 100);
		f.setVisible(true);
		data.add(20);
	}


}