package de.integrata.hkw04.brennelemente;

/**
 * Das Papier ist ein weiteres Brennelement. Der Ofen in der
 * ersten Version verbrennt nur Holz.
 *
 * @author Integrata Training AG
 * @version 1.3 krg 6.8.2003 (hkw02)
 */
public class Papier extends Holz {

  static {
    System.out.println("Papierstapel gefunden und geladen");
  }

  /**
   * Auch hier wollen wir zunächst keinen Default-Konstruktor,
   * da auch Holz keinen Default-Konstruktor hat, müssen wir 
   * einen geeigneten Konstruktor aufrufen.
   */
  public Papier(String typ) {
    super(typ);
    this.typ = typ;
  }
  
  public int brennen() {
  	System.out.println("Papier (" + typ + ") verbrennt.");
    return brennwert;
  }

  protected void finalize() throws Throwable {
  	System.out.println("GC: " + this.typ + " aufgeräumt.");
    super.finalize();
  } // finalize
} // class Papier

