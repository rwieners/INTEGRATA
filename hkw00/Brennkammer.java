package de.integrata.hkw00;

public class Brennkammer {

	Ofen ofen;
	
	public Brennkammer(Ofen o) {
		this.ofen = o;
	}
	
	/**
	 * Diese Methode nimmt zun�chst nur Elemente vom Typ Holz an.
	 * Sp�ter sollen dann alle brennbaren Elemente verbrannt werden k�nnen.
	 * Die Brennkammer erh�ht nach dem verbrennen die Temperatur des Ofens. 
	 *
	 * @version 1.4 krg 0.1115
	 */
	public void verbrennen(Holz b) {
	  int heizleistung = b.brennen();
	  
	  ofen.erh�heTemperatur(heizleistung); 
	  System.out.println("Temperatur: ist=" + ofen.getIstTemperatur()
	  		+ ", soll=" + ofen.getSollTemperatur());
	          
    }
 }
	  