package de.integrata.hkw03;

import de.integrata.hkw03.ofen.*;

/**
 * Batch-Start der HKW-Simulation.
 * Einsatz von Collections in OfenControllerImpl.
 */
public class JMain {
	public static void main(String[] args) {
	
		System.out.println("HKW Simulation V1.3.1115 gestartet. Starte OfenController");
		OfenControllerImpl oc = new OfenControllerImpl();
		//oc.start();
		oc.startSorted();
	}

}
