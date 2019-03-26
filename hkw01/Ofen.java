package de.integrata.hkw01;


/**
 * Der Ofen beschreibt das Modell unser Heizkraftwerksanwendung,
 * das heisst, hier ist der Geschäftsprozess abgebildet,
 * der Ofen wird beladen, Brennelemente werden verbrannt und
 * erhöhen die Temperatur des Ofens. Wird der Ofen zu heiss
 * wird automatisch gekühlt.
 *
 *
 * @version 1.4 krg 8.3.2010 (hkw01)
 */
public class Ofen  {
  
  private int istTemperatur = 20; 
  private int sollTemperatur;
  private int kuehlTemperatur = 30;
  private Brennkammer bk = null;
  
  /**
   * Der Ofen ist lediglich der Behälter für die Brennelemente
   * und die Brennkammer, die Erwärmung von Wasser und
   * Erzeugung des Stroms über Turbinen verbleibt als Übung :-)
   *
   * Die Brennkammer ist jetzt als innere Klasse implementiert, da sie
   * in Wirklichkeit ebenfalls innerer Bestandteil des Ofens ist.
   * Wir können mit inneren Klassn die Aggregation/Komposition realisieren.
   */
  public Ofen(int sollTemperatur) {
    this.sollTemperatur  = sollTemperatur;
    bk = new Brennkammer(this);	
  }
  
  /*
   * Getter und Setter für Properties
   */
  public int getIstTemperatur() { return this.istTemperatur; }
  public int getSollTemperatur() { return this.sollTemperatur; }
  public int getKuehlTemperatur() { return this.kuehlTemperatur; }
  public void setSollTemperatur(int i) { this.sollTemperatur = i; }  
  public void setKuehlTemperatur(int i) { this.kuehlTemperatur = i; }
  private void setIstTemperatur(int i) { this.istTemperatur = i; }
  
  /**
   * Diese Methode ist jetzt private, weil nur die Brennkammer
   * die Temperatur des Ofens erhöhen darf
   */  
  public void erhöheTemperatur(int brennwert) {
    setIstTemperatur(this.istTemperatur + brennwert);
  }
  
  private void kühlen() {
    setIstTemperatur(istTemperatur - kuehlTemperatur);
  }
  

  /**
   * In den Ofen können beliebige Objekte gegeben werden.
   * Die Elemente, die nicht Brennbar sind führen in der
   * ersten Fassung zum Programmabbruch
   *
   */
  public void beladen(Object o) {
  	System.out.println("Ofen.beladen mit " + o);
  	if (o == null) {
  		System.out.println("null-Objekt hat keinen Heizwert");
  	}
  	else {
  		try {
  			bk.verbrennen((Holz)o);
  		}
  		catch(ClassCastException e) {
  			System.out.println("Exception: " + e + " Objekt nicht brennbar");
  		}
  		catch(TemperaturException e) {
  			System.out.println("Exception: " + e); 
  			System.out.println(" --Attribute der T-Exception nutzen: "
  					+ e.getTemperatur()); 
  			this.kühlen();
  		}
  	}
  }
  
}
