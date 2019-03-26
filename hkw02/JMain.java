package de.integrata.hkw02;

import de.integrata.hkw02.ofen.*;

/**
 * hkw02 Batch-Start
 * @version 1.2.1115  
 */
public class JMain {

	public static void main(String[] args) {
	
		System.out.println("HKW Simulation V1.2.1115 gestartet. Starte OfenController");
		OfenControllerImpl oc = new OfenControllerImpl();
		oc.start();

		System.out.println(JMain.class.getTypeName() + " terminated");

	}

}
