package de.integrata.hkw05.ofen;

import java.util.*;
import java.io.*;

/**
 * ConfigurableOfenController
 * 
 * @author krg
 *  version 1.3	krg	29.6.04 ohne BrennelementeContainer
 *  version 2.6.107	krg - Java 5
 */
public class ConfigurableOfenController extends OfenControllerImpl {

	public static final String KEY_SOLL_TEMPERATUR = "ofen.sollTemperatur";
	public static final String KEY_IST_TEMPERATUR = "ofen.istTemperatur";
	public static final String KEY_KUEHL_TEMPERATUR = "ofen.kuehlTemperatur";
	

	/**
	 * Die Ofen Konfigurationsparameter werden jetzt über eine Properties-
	 * Datei gelesen. Falls dies nicht möglich ist, werden defaults verwendet.
	 */
	protected void leseOfenParameter() {
		String filename = "ofen.properties";
		try {
			Properties props = new Properties();
			InputStream is = this.getClass().getResourceAsStream(filename);
			props.load(is);
			System.out.println("Konfigurationsparameter aus " + filename + " gelesen");
			initSollTemperatur =
				Integer.parseInt(props.getProperty(KEY_SOLL_TEMPERATUR));
			initIstTemperatur =
				Integer.parseInt(props.getProperty(KEY_IST_TEMPERATUR));
			initKuehlTemperatur =
				Integer.parseInt(props.getProperty(KEY_KUEHL_TEMPERATUR));
		} catch (Exception e) {
			/*
			 * IOException oder NumberFormatException beim parseInt oder
			 * NullPointerException falls Property nicht gesetzt
			 */
			System.out.println(e	+ "Exception beim Lesen der Properties, verwende Defaults");
			super.leseOfenParameter();
		}
	}

	/**
	 * Einlesen der Brennelement-Liste erfolgt jetzt über eine Properties-Datei.
	 * Die Datei soll im Classpath gespeichert sein.
	 * <p>
	 * Optional realisieren: 
	 * Zusätzlich zu den in der Datei gespeicherten Brennelemnten soll immer
	 * auch Kohle und Gas in einem Container verbrannt werden.</p>
	 */
	//Cast von Properties auf Map<String, String> Problem
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map<String, String> leseBrennelementeListe() {
		String fname = "brennelemente.properties";
		try {
			Properties props = new Properties();
			InputStream is = this.getClass().getResourceAsStream(fname);
			props.load(is);
			System.out.println("Brennelement-Liste aus " + fname + " gelesen");
			return (Map)props;
		}
		catch (Exception e) {
			/*
			 * IOException oder NumberFormatException beim parseInt oder
			 * NullPointerException falls Property nicht gesetzt
			 */
			System.out.println(e + " beim Lesen der Brennelemente-Properties ");
			return super.leseBrennelementeListe();
		}
	}
}
