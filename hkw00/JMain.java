package de.integrata.hkw00;

/**
 * Diese Startklasse dient dem Test des Ofen-Modells.
 * 
 * @version 1.5 krg 8.11.2010 (hkw00) package integrata
 */
public class JMain {

	/**
	 * Starte Simulation
	 * 
	 */
	public static void main(String[] args) {
		Holz h;
		
		System.out.println("Projekt Heizkraftwerk Version 0.1115");
	
		Ofen ofen = new Ofen(100);

		for (int i = 0; i < 20; i++ ) {
			h = new Holz("Buche");
			ofen.beladen(h);
		
			h = new Holz("Fichte");
			ofen.beladen(h);
		}
		
	}
}
