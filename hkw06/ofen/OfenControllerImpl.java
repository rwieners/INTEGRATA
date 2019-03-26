package de.integrata.hkw06.ofen;

import de.integrata.hkw06.util.*;

import java.util.*;

/**
 * Der Controller nutzt die ObjectFactory zur dynamischen Objekterzeugung.
 * Die Ofenparameter stehen als Defaultwerte fest in der Source.
 * Die verwendbaren Objekte sind in der getBrennelemente-Methode hart kodiert.
 * <p>
 * Der OfenController verwendet als Modell das Kraftwerk (den Ofen).
 * 
 * version 1.3		krg 22.7.04: start() entfernt und in den Konstruktor verlagert
 * version 1.4 krg 23.4.05: Anpassung auf Java 5
 * @version 2.6.107	krg Geänderte Reihenfolge MVC nach GUI: getter für Ofen
 */
public class OfenControllerImpl {
	private Ofen ofen;
	private Map<String, String> beListe;
	protected int initSollTemperatur;
	protected int initIstTemperatur;
	protected int initKuehlTemperatur;

  /** Getter: ofen */
  public Ofen getOfen() {
    return this.ofen;
  }

	/**
	 * Im Konstruktor wird das Logging initialisiert, die Parameter für 
	 * das Kraftwerk gelesen eine Ofen-Instanz erzeugt und die Parameter
	 * gesetzt.<br>
	 * Abschließend wird eine Liste der Brennelemnten geladen.
	 */
	public OfenControllerImpl() {
		leseOfenParameter();
		ofen = new Ofen(initSollTemperatur);
		ofen.setIstTemperatur(initIstTemperatur);
		ofen.setKuehlTemperatur(initKuehlTemperatur);
		beListe = leseBrennelementeListe();
	}


	/**
	 * Mittels Zufahlszahlengenerator der JVM wird eine Nummer für
	 * ein Brennelemnet erzeugt.
	 * Die Brennelemente werden über eine Factory dynamisch erzeugt.
	 * Die Brennelemnteerzeugung ist in die Ofen-Steuerung ausgelagert,
	 * es wird nur eine Kennung (== Integernummer) des gewünschten
	 * Brennelemnts übermittelt.
	 */
	public void zufallsVerbrennen() {
		int anzahl = 30;
		int rand;
		Random random = new Random();
	
		/*
		 * Die Brennelemnt-Liste(HashMap) in ein Array transferieren
		 */
		String[] keyArray = this.getBeKeys();
		
		String beKey;		//Brennelement Key zur Auswahl des vollqual. Klassennamen
		for (int i = 0; i < anzahl; i++) {
			rand = random.nextInt(keyArray.length);
			beKey = (String)keyArray[rand];
			verbrennen(beKey);
			System.gc();	 // expliziter Aufruf des Garbage Collectors
		}
	}

	/*
	 * Getter
	 */
	public int getIstTemperatur() {
		return ofen.getIstTemperatur();
	}
	public int getSollTemperatur() {
		return ofen.getSollTemperatur();
	}
	public int getKuehlTemperatur() {
		return ofen.getKuehlTemperatur();
	}


	/**
	 * Hier werden die Initialwerte der Ofen-Properties versorgt.
	 * Z.Z. noch fest codiert.
	 */
	protected void leseOfenParameter() {
		initSollTemperatur = 100;
		initIstTemperatur = 15;
		initKuehlTemperatur = 30;
	}

	/**
	 * Liefere ein Array der Schlüssel Brennelementeliste
   * Demonstriert die Wandlung einer Collection in ein Array und
   * ist auch nur deshalb hier so implementiert.
	 */
	public String[] getBeKeys() {
		int beSize = beListe.size();		//Anzahl der Elemente
        //Extrahiere die Keys in eine Liste
		Collection<String> keyList = beListe.keySet();
		String[] keyArray = new String[beSize];
		keyArray = (String[])keyList.toArray(keyArray);
    //    keyArray = keyList.toArray(keyArray);
		return keyArray;
	}
	 

	/**
	 * Lese eine Liste der verfügbaren Brennelemente und speichere sie in
	 * einer HashMap. Das Beladen des Ofens erfolgt aus dieser HashMap.
	 * Die Keys der Map enthalten die Brennelementekennung (String).
	 * Die Values enthalten einen String mit den Teilen:<br>
	 * <ul>
	 * <li>vollquallifizierter Klassenname</li>
	 * <li>Brennelementetyp</li>
	 * </ul>
	 * die durch das Zeichen ';' getrennt sind.
	 * Z.Z. noch fest codiert.
	 * 
	 * @return eine HashMap mit Namen und Typ der Brennelemente
	 */
	protected Map<String, String> leseBrennelementeListe() {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Buchenholz", "de.integrata.hkw06.brennelemente.Holz; Buche");
		hm.put("Fichtenholzholz", "de.integrata.hkw06.brennelemente.Holz; Fichte");
		hm.put("Papier", "de.integrata.hkw06.brennelemente.Papier; Zeitung");
		hm.put("Sofa", "de.integrata.hkw06.moebel.Sofa; Stoff");
		hm.put("Sofa2", "de.integrata.hkw06.moebel.Sofa");	//nicht erzeugbar
		hm.put("Dose1", "de.integrata.hkw06.brennelemente.Dose; Aluminium");
		hm.put("Dose2", "de.integrata.hkw06.brennelemente.Dose; Zinn");
		hm.put("Schrank", "de.integrata.hkw06.moebel.MetallSchrank; Blech");
		return hm;
	}

	/**
	 * Verbrennt ein Brennelementobjekt, das über den Schlüssel-Parameter
	 * der Brennelementeliste bestimmt wird.
	 * Das Objekt wird dynamisch mittels Factory erzeugt.
	 * 
	 * @param beKey Schlüssel des Brennelements
	 */
	public void verbrennen(String beKey) {
		String beInfo = (String)beListe.get(beKey);
		System.out.println("Belade " + beKey + "=" + beInfo);
		Object obj = createObject(beInfo);
		ofen.beladen(obj);
		obj = null;
		System.gc();
	}

	/**
	 * Erzeuge ein Brennelement-Objekt mittels ObjectFactory
	 * 
	 * @param pBrennelementInfo Klassenname des Brennelements
	 *  oder Klassenname und Typ durch Zeichen ';' getrennt. (siehe auch
	 *  Hashmap Brennelementliste)
	 * @return ein Brennelement-Objekt
	 */
	protected Object createObject(String pBrennelementInfo) {
		StringTokenizer tok = new StringTokenizer(pBrennelementInfo, ";");
		String className;
		String type;
		Object o = null;

		if (tok.countTokens() == 1) {
			className = pBrennelementInfo;
			//Erzeuge ein Brennelementobjekt mittels Klassenname
			o = ObjectFactoryImpl.getInstance().create(className);
	} else {
			className = tok.nextToken();
			type = tok.nextToken().trim();
			//Erzeuge ein Brennelementobjekt mittels Klassenname und Brennelement-Typ
			o = ObjectFactoryImpl.getInstance().create(className, type);
		}
		return o;
	}
}
