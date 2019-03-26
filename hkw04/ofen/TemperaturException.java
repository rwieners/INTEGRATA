package de.integrata.hkw04.ofen;

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
 * @version 1.3	krg 20.5.2005 (hkw02)
 */
public class TemperaturException extends Exception {
	private static final long serialVersionUID = 1L;

  private int temperatur;
  
  public TemperaturException(int temp) {
    super("Brennkammer zu heiss: Temperatur=" + temp + " Grad!");
    this.temperatur = temp;
  }
  
  public int getTemperatur() {
    return temperatur;
  }
  
}
