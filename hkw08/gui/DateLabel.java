package de.integrata.hkw08.gui;

import java.util.Date;
import java.awt.Label;
import java.text.SimpleDateFormat;

/**
 * Das DateLabel zeigt die laufende Uhrzeit an, da die GUI
 * weiterhin reagieren muß, wird das DateLabel mit Threads
 * realisiert
 *
 * @version 1.8.1115
 */ 
class DateLabel extends Label implements Runnable {
	private static final long serialVersionUID = 1L;

  private SimpleDateFormat sdf = new SimpleDateFormat(
  		"'Datum' dd.MM.yyyy   '   Zeit:' HH:mm:ss");
	private String dateString;
  private Date d = new Date();

  public DateLabel() {
    setAlignment(Label.CENTER); 
    setText(getDateString());   
    // so gehts nicht!      run();
    // Anwendung ist dann blockiert!
    //Der Thread muss gestartet werden und wird durch den Thread-Scheduler aktiviert
    Thread t = new Thread(this);
    t.setDaemon(true);
    t.start();
  }

  /**
   * liefert das formatierte Datum in Form eines Strings zurück
   */
  public String getDateString() {
    dateString = sdf.format(d);
    //System.out.println(dateString);
    return dateString;
  }

  /**
   * Das Runnable-Interface verpflichtet zur Implementierung
   * der run()-Methode. Diese wird in einem eigenständigen Thread
   * ablaufen. Da es keinen Sinn macht, dieselbe Uhrzeit
   * mehrmals pro Sekunde anzuzeigen, wird sleep() aufgerufen.
   */
  public void run() {

		while(true) {
	    //Date d = new Date();  		// nicht performant
	    d.setTime(System.currentTimeMillis());	// das ist schneller
	    setText(getDateString());		// Eigenschaft des Labels dyn. ändern
	    try {
	    	// Bei 1000 msec könnte eine Sekunde übersprungen werden
	      Thread.sleep(950);
	    }
	    catch (InterruptedException ie) {
	      System.err.println("DateLabel.run: " + ie);
	    }
    }
  }

}
