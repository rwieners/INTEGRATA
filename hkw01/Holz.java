package de.integrata.hkw01;


 /**
 * Das Brennelement Holz besitzt die Methode brennen(), die
 * von der Brennkammer des Ofens aufgerufen wird.
 *
 * @version 0.1115
 */
public class Holz  {
  
  /**
   * Anhand vom Holztyp kann der Brennwert definiertwerden.
   * Typen sind u.a. "Buche", "Eiche" usw.
   * protected damit die Kinder(z.B. Papier) darauf zugreifen können!
   */
  protected String typ;
  protected int brennwert = 0;


  /** 
   * Wir wollen mit Absicht *keinen* parameterlosen Konstruktor, der
   * Compiler fügt diesen nun auch nicht hinzu, da wir einen
   * speziellen Konstruktor angeben. Für die Vererbung (Papier)
   * wird dies eine Rolle spielen.
   */
  public Holz(String typ) {
    this.typ = typ;
    if (typ.startsWith("Bruchholz")) brennwert = 3;
    else if (typ.startsWith("Fichte")) brennwert = 4;
    else if (typ.startsWith("Buche")) brennwert = 5;
    // es gibt kein "Default-Holz"!
    else brennwert = 0;	//default brennwert
  }
  
/*
  public Holz() {
    this("Fichte");   
  } // Holz()
*/

  /**
   * Ausgabe, daß das Element verbrannt wird.
   * Rückseten des Brennwertes, ein verbranntes Objekt (Asche) liefert keine
   * Energie und erhöht die Temperatur des Ofens nicht weiter.
   */
  public int brennen() {
    System.out.println(typ + " verbrennt mit brennwert="
    		+ this.brennwert);
    int bw = this.brennwert;
    this.brennwert = 0;
    return bw;
  }
  
  @Override
  public String toString() {
  	return "Holz vom typ=" + this.typ + " mit brennwert=" + this.brennwert
  			+ ". Anstelle von " + super.toString();
  }
}
