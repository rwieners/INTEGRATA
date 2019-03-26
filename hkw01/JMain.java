package de.integrata.hkw01;

/**
 * Diese Startklasse dient dem Test des Ofen-Modells,
 * 
 * @version 0.1115
 */
public class JMain {

	/**
	 * Starte Simulation
	 */
	public static void main(String[] args) {
		Holz h;
		Papier p;
		
		System.out.println("Projekt Heizkraftwerk Version 01");
	
		Ofen ofen = new Ofen(100);

		for (int i = 0; i < 20; i++ ) {
			h = new Holz("Buche");
			ofen.beladen(h);
		
			h = new Holz("Fichte");
			ofen.beladen(h);
		
			p = new Papier("Altpapier");
			ofen.beladen(p);

			Dose d = new Dose("Blech");
			ofen.beladen(d);
		}
		
	}
}
