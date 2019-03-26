package de.integrata.hkw08.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import de.integrata.hkw08.ofen.*;

/**
 * 
 */
public class TemperaturDateDisplay extends JLabel {
	private static final long serialVersionUID = 1L;

	private SimpleDateFormat sdf = new SimpleDateFormat(
	"'Datum' dd.MM.yyyy   '   Zeit:' HH:mm:ss");
	private String dateString;
	private Date d = new Date();
	OfenControllerImpl oc;
	Timer t;
	
	public TemperaturDateDisplay(OfenControllerImpl oc) {
		this.oc = oc;
		t = new Timer(true);
		TimerTask task = new SecTimer();
		t.schedule(task, 0, 1000);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setText(getDateString());
	}


	/**
	 * liefert das formatierte Datum in Form eines Strings zurück
	 */
	public String getDateString() {
		dateString = sdf.format(d);
		return dateString;
	}

	public static void main(String[] args) {
		new TemperaturDateDisplay(new OfenControllerImpl());
	}
	
	// Inner class start ---------------------------------------------------
	private class SecTimer extends TimerTask {
		@Override
		public void run() {
			d = new Date();
			String text = sdf.format(d) + "      Temperatur: "
					+ oc.getOfen().getIstTemperatur();
			TemperaturDateDisplay.this.setText(text);
		}
	}
	// Inner class end -----------------------------------------------------
	
}
