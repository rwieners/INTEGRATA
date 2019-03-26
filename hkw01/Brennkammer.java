package de.integrata.hkw01;


public class Brennkammer {

	Ofen ofen;
	
	public Brennkammer(Ofen o) {
		this.ofen = o;
	}
	
	/**
	 * Diese Methode nimmt Elemente entgegen, die Brennbar sind
	 * zun�chst Holz, dann alle brennbaren Elemente. Die
	 * Brennkammer erh�ht die Temperatur des Ofens. 
	 *
	 * Polymorphie:
	 * zur Laufzeit wird die richtige brennen()-Methode des Brennelements
	 * aufgerufen(Holz, Papier, Gas), da der Ofen lt. Signatur nicht
	 * weiss was f�r ein Objekt er bekommt!
	 * 
	 * Gezeigt werden die verschiedenen M�glichkeiten auf Methoden, 
	 * Attribute der �u�eren Klasse zuzugreifen, diese k�nnen sogar
	 * private sein!
	 * 
	 * @version 1.4 krg 8.3.2004 (hkw00)
	 */
	public void verbrennen(Holz b) throws TemperaturException {
	  int heizleistung = b.brennen();
	  
	  ofen.erh�heTemperatur(heizleistung); // Mit Namen der �u�eren Klasse
	  System.out.println("Temperatur: ist=" + ofen.getIstTemperatur()
	  		+ ", soll=" + ofen.getSollTemperatur());
	  
	  if (ofen.getIstTemperatur() > ofen.getSollTemperatur()) {
	  	throw new TemperaturException(ofen.getIstTemperatur());
	  }
	          
    }
 }
	  