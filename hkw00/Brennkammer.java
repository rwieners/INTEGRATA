package de.integrata.hkw00;

public class Brennkammer {

	Ofen ofen;
	
	public Brennkammer(Ofen o) {
		this.ofen = o;
	}
	
	/**
	 * Diese Methode nimmt zunächst nur Elemente vom Typ Holz an.
	 * Später sollen dann alle brennbaren Elemente verbrannt werden können.
	 * Die Brennkammer erhöht nach dem verbrennen die Temperatur des Ofens. 
	 *
	 * @version 1.4 krg 0.1115
	 */
	public void verbrennen(Holz b) {
	  int heizleistung = b.brennen();
	  
	  ofen.erhöheTemperatur(heizleistung); 
	  System.out.println("Temperatur: ist=" + ofen.getIstTemperatur()
	  		+ ", soll=" + ofen.getSollTemperatur());
	          
    }
 }
	  