package de.integrata.hkw02.ofen;

import de.integrata.hkw02.util.*;

/**
 * Der Controller nutzt die ObjectFactory zur dynamischen Objekterzeugung.
 * Die Ofenparameter stehen als Defaultwerte fest in der Source.
 * <p>
 * Der OfenController verwendet als Modell das Kraftwerk (den Ofen).
 * </p>
 * @version 1.2.1115
 */

public class OfenControllerImpl {
	private static final int T_IST = 20;
	private static final int T_SOLL = 100;
	private static final int T_KUEHL = 30;
	private Ofen ofen;
	ObjectFactory factory = ObjectFactoryImpl.getInstance();

	/*
	 * Die Realisierung durch zwei Arrays ist nicht besonders stabil und 
	 * schon gar nicht änderungsfreundlich.
	 */
	String[] beList = { 
			"de.integrata.hkw02.brennelemente.Holz",
			"de.integrata.hkw02.brennelemente.Holz",
			"de.integrata.hkw02.brennelemente.Papier",
			"de.integrata.hkw02.brennelemente.Dose",
			"de.integrata.hkw02.moebel.Sofa",
			"de.integrata.hkw02.moebel.MetallSchrank",
			"de.integrata.hkw02.ungültiger.Klassenname"
	};

	String[] paramValues = { 
			"Buche",
			"Fichte",
			"Altpapier",
			"Cola",
			"Stoff",
			"Alu",
			"dummy"
	};

	/**
	 * Instanzire den Ofen und setze die initialen Ofentemparaturen.
	 *
	 */
	public OfenControllerImpl() {
		ofen = new Ofen(T_SOLL);
		ofen.setIstTemperatur(T_IST);
		ofen.setKuehlTemperatur(T_KUEHL);
		System.out.println("Ofen erzeugt mit: ist=" + ofen.getIstTemperatur()
				+ ", soll=" + ofen.getSollTemperatur() + ", kühl=" + ofen.getKuehlTemperatur());
	}
	
	
	/**
	 * Starte den Verbrennvorgang des Ofens im Batch-Mode.
	 * Die Namen der Brennelemente werden fest aus einer Liste gelesen und
	 * durch die Factory erzeugt und in den Ofen geladen.
	 *
	 */
	public void start() {
		for (int i = 0; i < 10; i++) {
			for (int k = 0; k < beList.length; k++) {
				String cname = beList[k];
				String conParam = paramValues[k];
				Object o = factory.create(cname, conParam);
				if (o != null) {
					System.out.println(o.getClass().getSimpleName() + " wurde erzeugt");
					ofen.beladen(o);
				}
			}
			System.gc();
		}
	}
	

}
