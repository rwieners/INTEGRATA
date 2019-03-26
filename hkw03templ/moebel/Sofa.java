package de.integrata.hkw03templ.moebel;

import de.integrata.hkw03templ.global.Brennbar;

/**
 * Ein Sofa ist ein Brennbares Möbelstück, es implemtiert 
 * daher das Interface global.Brennbar
 *
 * @version 2.6.107 krg hkw03 Sortierbare Brennelemente
 */
public class Sofa extends Moebel implements Brennbar {

	private int brennwert = 10;
	
  public Sofa(String typ) {
    super(typ);
  }

  public int brennen() {
  	System.out.println(this.typ + " verbrennt");
    return this.brennwert;	//Alle Sofas brennen gut
  }

  protected void finalize() throws Throwable {
  	System.out.println("GC: " + this.typ + " aufgeräumt.");
    super.finalize();
  }
  
  public String toString() {
  	return this.getClass().getSimpleName() + ": typ=" + this.typ
  			+ ", brennwert=" + this.brennwert;
  }
 
}
