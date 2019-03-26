package de.integrata.hkw00;


 /**
 * Das Brennelement Holz besitzt die Methode brennen(), die
 * von der Brennkammer des Ofens aufgerufen wird.
 *
 * @version 1.3 krg 2.5.2005
 */
public class Holz  {
  
  /**
   * Anhand vom Holztyp kann der Brennwert definiertwerden.
   * Typen sind u.a. "Buche", "Eiche" usw.
   * protected damit die Kinder(z.B. Papier) darauf zugreifen k�nnen!
   */
  protected String typ;
  protected int brennwert = 0;


  /** 
   * Wir wollen mit Absicht *keinen* Default-Konstruktor, der
   * Compiler f�gt diesen nun auch nicht hinzu, da wir einen
   * speziellen Konstruktor angeben. F�r die Vererbung (Papier)
   * wird dies eine Rolle spielen.
   */
  public Holz(String typ) {
    this.typ = typ;
    if (typ.startsWith("Bruchholz")) brennwert = 3;
    else if (typ.startsWith("Fichte")) brennwert = 4;
    else if (typ.startsWith("Buche")) brennwert = 5;
  }

/*
  public Holz() {
    this("Fichte");   
  } // Holz()
*/

  /**
   * Lediglich die Ausgabe, da� das Element verbrannt wird.
   * Eine Verbesserung w�re die R�ckgabe des Brennwertes und
   * somit dynamische Erh�hung der Temperatur des Ofens.
   */
  public int brennen() {
    System.out.println(this.getClass().getSimpleName()
    		+ " vom typ=" + this.typ +
    		" verbrennt mit brennwert=" + this.brennwert);
    return brennwert;
  }
  
}
