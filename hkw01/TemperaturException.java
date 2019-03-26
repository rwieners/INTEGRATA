package de.integrata.hkw01;

/**
 * Hier wird eine eigene sog. User-Exception definiert,
 * sie zeigt an, daß die istTemperatur des Ofens die
 * soll Temperatur übersteigt.
 * 
 * Wir sorgen mit super() dafür, daß der Benutzer mit
 * getMessage() auf die Fehlermeldung zugreifen kann, ohne
 * dass wir diese Methode überschreiben.
 *
 * Wichtig ist die tatsächliche Temperatur in der Brennkammer,
 * falls die Temperatur viel zu hoch ist (Brennkammer bereits 
 * beschädigt) könnte ein von TemperaturException abgeleitete
 * VielZuHeissException geworfen werden.
 *
 * Um die Temperatur zu ermitteln wird die getTemperatur()-Methode
 * hinzugefügt.
 * 
 * @version 1.6	krg 29.7.2011 (hkw01)
 */
public class TemperaturException extends Exception {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int temperatur;
  
  /**
   * Der Konstruktor erwartet einen Meldungstext und die Isttemperatur.
   * 
   * @param msg		Individueller Meldungstext
   * @param temp	aktuelle Isttemperatur
   */
  public TemperaturException(String msg, int temp) {
    super(msg);
    this.temperatur = temp;
  }
  
  /**
   * Konstruktor mit konstantem Meldungstext.
   * 
   * @param temp	aktuelle Isttemperatur
   */
  public TemperaturException(int temp) {
    this("Brennkammer zu heiss: Temperatur=" + temp + " Grad!", temp);
  }
  
  public int getTemperatur() {
    return temperatur;
  }
  
}
