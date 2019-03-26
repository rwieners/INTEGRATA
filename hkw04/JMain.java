package de.integrata.hkw04;
import de.integrata.hkw04.ofen.*;

/**
 * Diese Startklasse dient dem Test des Ofen-Modells,
 * 
 * Um das Simulationsprogramm JMain von unterschiedlichen Ofen-
 * Implementierungen zu entkoppeln wurde eine einheitliche Steuerung 
 * der OfenController eingeführt. Der OfenController erzeugt den Ofen,
 * ermöglicht die dynamische Generierung von Brennelemnten und befeuert
 * den Ofen.
 * 
 * @version 1.4.1115
 * 		Einlesen der Ofenparameter und Brennelementeliste über Properties-Dateien.
 */
public class JMain {

	/**
	 * Starte Simulation
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Projekt Heizkraftwerk Version 1.4.1115");
		OfenControllerImpl oc = new ConfigurableOfenController();
		oc.start();
	}
}
