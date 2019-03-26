/*TempDisplay*/
package de.integrata.hkw07.gui;

import java.awt.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.integrata.hkw07.ofen.*;

/**
 * Das TempDisplay zeigt die Temperatur des Ofens an. Das Display soll jedoch
 * nicht via Polling beim Ofen nachschauen, ob sich die Temperatur geändert hat,
 * sondern der Ofen soll über das Model View Controler Konzept mittels Observer
 * Design-Pattern das Display benachrichtigen.
 * 
 * @version 1.3 4.5.03, krg: use Argument in update method
 */
public class TempDisplayGraph extends JFrame {
	private static final long serialVersionUID = 1L;

	private Ofen ofen;

	public TempDisplayGraph(Observable ofen) {
		this.ofen = (Ofen) ofen;
		System.out.println("TempDisplay(): Registrierung beim Model");

		// GUI stuff
		this.setLayout(new GridLayout(1, 1));
		this.setTitle(this.getClass().getSimpleName());
		this.setLocation(10, 10);
		this.setSize(400, 300);
		this.setResizable(false);

		JPanel p = new GraDis();
		this.add(p);
		ofen.addObserver((Observer)p);
		this.setVisible(true);
	}

	class GraDis extends JPanel implements java.util.Observer {
		private static final long serialVersionUID = 1L;

		int xmax, ymax;

		int x, y;

		private GraDis() {
			this.setBackground(Color.YELLOW);
			xmax = this.getWidth();
			ymax = this.getHeight();
			System.out.println("panelsize=(" + xmax + ", " + ymax + ")");
			System.out.println(this.getSize());
			x = 0;
			y = 260;
		}

		@SuppressWarnings("rawtypes")
		private Vector line = new Vector();
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			for (int i = 0; i < line.size(); i++) {
				System.out.println(line.get(i));
			}
		}

		/**
		 * Die Benachrichtigung einer Änderung des Models
		 */
		private int istTemp;

		public void update(Observable o, Object arg) {
			if (arg == null) {
				// Die neuen Daten vom Ofen holen und anzeigen
				// lblTemp.setText(" " + ((Ofen)ofen).getIstTemperatur());
			} else { // IstTemperatur wird als Argument geliefert
				Integer io = (Integer) arg;
				this.istTemp = io.intValue();
				System.out.println("TempDisplayGraph: update: istTemp=" + this.istTemp);
				drawLine(istTemp);
				this.repaint();
			}
		}

		private void drawLine(int istTemp) {
			x2 = x1 + 10;
			y2 = y1 + istTemp;
		}
		
	   public void paint(Graphics g) {
	  	 g.setColor(Color.RED);
	  	 g.drawLine(x1, y1, x2, y2);
       g.drawLine(50,50,200,200);
   }

			private int x1 = 40;
			private int y1 = 40;
			private int x2;
			private int y2;
	}	//end GraDis
	
		
}
