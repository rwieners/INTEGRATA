package de.integrata.hkw03templ;

import de.integrata.hkw03templ.ofen.*;

/**
 * Batch-Start der HKW-Simulation.
 * Einsatz von Collections in OfenControllerImpl.
 * {@link OfenControllerImpl#startSorted()} ist noch nicht fertig implementiert. 
 */
public class JMain {
	public static void main(String[] args) {
	
		System.out.println("HKW Simulation V1.3.1115 gestartet. Starte OfenController");
		OfenControllerImpl oc = new OfenControllerImpl();
		//oc.start();
		oc.startSorted();
	}

}
