package de.integrata.hkw03templ.ofen;

import de.integrata.hkw03templ.global.Brennbar;

/**
 * Der Ofen beschreibt das Modell unser Heizkraftwerksanwendung,
 * das heisst, hier ist der Geschäftsprozess abgebildet,
 * der Ofen wird beladen, Brennelemente werden verbrannt und
 * erhöhen die Temperatur des Ofens. Wird der Ofen zu heiss
 * wird automatisch gekühlt.
 *
 * Als Programmiertechniken werden benutzt:
 * - Casting der brennbaren Elemente
 * - Garbage Collection der Brennelemente
 * - innere Klasse
 * - packages für Rollenverteilung
 * - Exception Handling
 * - Verwendung von User-Exceptions
 *
 * @version 1.3.1115
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
    //die Brennkammer hat jetzt einen parameterlosen Konstruktor
    bk = new Brennkammer();	
  }
  
  /*
   * Getter und Setter für Properties
   */
  public int getIstTemperatur() { return this.istTemperatur; }
  public int getSollTemperatur() { return this.sollTemperatur; }
  public int getKuehlTemperatur() { return this.kuehlTemperatur; }
  public void setSollTemperatur(int i) { this.sollTemperatur = i; }  
  public void setKuehlTemperatur(int i) { this.kuehlTemperatur = i; }
  public void setIstTemperatur(int i) { this.istTemperatur = i; }
  
  /**
   * Diese Methode ist jetzt private, weil nur die Brennkammer
   * die Temperatur des Ofens erhöhen darf
   */  
  private void erhöheTemperatur(int brennwert) {
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
   * Die Exceptions werden nun abgefangen.
   * Falls die Temperatur in der Brennkammer zu hoch ist
   * wird eine Exception abgefangen und der Ofen wird
   * automatisch gekühlt
   */
  public void beladen(Object o) {
  	if (o == null) {
  		System.out.println("null-Objekt hat keinen Heizwert");
  	}
  	else {
	    try {
	      bk.verbrennen((Brennbar)o);
	    }
	    catch(ClassCastException cce) {
	    	System.out.println(cce + ": " + o.getClass().getName() + " nicht brennbar");
	    }
	    catch(TemperaturException te) {
	    	System.out.println(te.toString());
	      kühlen();
	    }  
		}
	}


	//=========================================================================
  /**
   * Die Brennkammer als member inner class:
   * Kein Konstruktor mit Referenz auf Ofen mehr nötig
   * Direkter Zugriff auf private Attribute von Ofen oder mit Ofen.this.attr
   */
  private class Brennkammer {
  	
    /** wird bei einer inner class nicht benötigt
    public Brennkammer(Ofen o) {
      super();
    } // Brennkammer()
    */
    
    /**
     * Diese Methode nimmt Elemente entgegen, die Brennbar sind
     * zunächst Holz, dann alle brennbaren Elemente. Die
     * Brennkammer erhöht die Temperatur des Ofens. 
     *
     * Polymorphie:
     * zur Laufzeit wird die richtige brennen()-Methode des Brennelements
     * aufgerufen(Holz, Papier, Gas), da der Ofen lt. Signatur nicht
     * weiss was für ein Objekt er bekommt!
     * 
     * Gezeigt werden die verschiedenen Möglichkeiten auf Methoden, 
     * Attribute der äußeren Klasse zuzugreifen, diese können sogar
     * private sein!
     */
    public void verbrennen(Brennbar b) throws TemperaturException {
      int heizleistung = b.brennen();
      Ofen.this.erhöheTemperatur(heizleistung); // Mit Namen der äußeren Klasse
      System.out.println("Temperatur: ist=" + istTemperatur + ", soll=" + sollTemperatur);
          
      if (istTemperatur >= sollTemperatur) {
        throw new TemperaturException(istTemperatur);
      }
    }
  } // inner class Brennkammer
	//====================================================================
  
}
