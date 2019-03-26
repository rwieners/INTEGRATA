package de.integrata.hkw01;


 /**
 * @version 0.1115
 */
public class Papier extends Holz {
  
	//Doppelte (überschattete) Variablen vermeiden!
  //protected String typ;
  //protected int brennwert = 0;


  /** 
   * Es ist Aufgabe des papier-Konstruktors den Brennwert zu definieren!
   */
  public Papier(String typ) {
  	super(typ);
    //this.typ = typ;
  	super.brennwert = 2;
  	//Diskutiere Zugriff auf protected Variablen!
  }


  /**
   * Diskutiere Ausgabe beim brennen: "Holz verbrennt ..."
   * 
  public int brennen() {
    System.out.println(typ + " verbrennt mit brennwert="
    		+ this.brennwert);
    return brennwert;
  }
   */
  
}
