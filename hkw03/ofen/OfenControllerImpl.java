package de.integrata.hkw03.ofen;

import de.integrata.hkw03.global.Brennbar;
import de.integrata.hkw03.util.*;
import de.integrata.hkw03.ofen.Ofen;

import java.util.*;

/**
 * Der Controller nutzt die ObjectFactory zur dynamischen Objekterzeugung. Die
 * Ofenparameter stehen als Defaultwerte fest in der Source. Die verwendbaren
 * Objekte sind in der getBrennelemente-Methode hart kodiert.
 * <p>
 * Der OfenController verwendet als Modell das Kraftwerk (den Ofen).
 * 
 * @version 1.3 krg 22.7.04: start() entfernt und in den Konstruktor verlagert
 * @version 1.4 krg 23.4.05: Anpassung auf Java 5
 */
public class OfenControllerImpl {
	private static final int T_IST = 20;
	private static final int T_SOLL = 100;
	private static final int T_KUEHL = 30;

	protected int initSollTemperatur;
	protected int initIstTemperatur;
	protected int initKuehlTemperatur;

	private Ofen ofen;
	ObjectFactory factory = ObjectFactoryImpl.getInstance();
	/** Map mit Brennelementnamen und weiteren Infos zur Erzeugung der Elemente */
	private Map<String, String> beListe;	

	/**
	 * Im Konstruktor wird das Logging initialisiert, die Parameter für das
	 * Kraftwerk gelesen eine Ofen-Instanz erzeugt und die Parameter gesetzt.<br>
	 * Abschließend wird eine Liste der Brennelemnten geladen.
	 */
	public OfenControllerImpl() {
		leseOfenParameter();
		ofen = new Ofen(initSollTemperatur);
		ofen.setIstTemperatur(initIstTemperatur);
		ofen.setKuehlTemperatur(initKuehlTemperatur);
		beListe = leseBrennelementeListe();
		System.out.println("Ofen erzeugt mit: ist=" + ofen.getIstTemperatur()
				+ ", soll=" + ofen.getSollTemperatur() + ", kühl=" + ofen.getKuehlTemperatur());

	}

	/**
	 * Mittels Zufahlszahlengenerator der JVM wird eine Nummer für ein
	 * Brennelemnet erzeugt. Die Brennelemente werden über eine Factory
	 * dynamisch erzeugt. Die Brennelemnteerzeugung ist in die Ofen-Steuerung
	 * ausgelagert, es wird nur eine Kennung (== Integernummer) des gewünschten
	 * Brennelemnts übermittelt.
	 */
	public void start() {
		int anzahl = 30;
		int rand;
		Random random = new Random();

		/*
		 * Die Brennelemnt-Liste(HashMap) in ein Array transferieren
		 */
		String[] keyArray = this.getBeKeys();

		String beKey; // Brennelement Key zur Auswahl des vollqual. Klassennamen
		for (int i = 0; i < anzahl; i++) {
			rand = random.nextInt(keyArray.length);
			beKey = (String) keyArray[rand];
			verbrennen(beKey);
			System.gc(); // expliziter Aufruf des Garbage Collectors
		}
	}
	
	/**
	 * Verbrennen von Brennelementen, die nach ihrem Heizwert sortiert
	 * wurden.
	 *
	 */
	public void startSorted() {
		List<Object> objListe = new ArrayList<Object>();
		String[] keyArray = this.getBeKeys();
		for (int i = 0; i < keyArray.length; i++) {
			String  beInfo = beListe.get(keyArray[i]);
			System.out.println("unsorted beInfo=" + beInfo);
			objListe.add(createObject(beInfo));
		}

		System.out.println("\n==== Unsortierte Objektliste");
		List<Brennbar> liste2 = new ArrayList<Brennbar>();
		for (int i = 0; i < objListe.size(); i++) {
			Object o = objListe.get(i);
			if (o instanceof Brennbar) {
				System.out.println("Add: " + o);
				liste2.add((Brennbar)o);
			}
			else {
				System.out.println("----startSorted: " + o + " filtern, ist nicht vom Typ=Brennbar!");
			}
		}

		System.out.println("\n==== Sortierte Objektliste über Iterator");
		Collections.sort(liste2);
		Iterator<Brennbar> it = liste2.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		//Belade den Ofen mit den sortierten Elementen
		for (int i = 0; i < 6; i++) {
			for (Object be : liste2) {
				ofen.beladen(be);
				be = null;
				System.gc();
			}
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
	 * Hier werden die Initialwerte der Ofen-Properties versorgt. Z.Z. noch fest
	 * codiert.
	 */
	protected void leseOfenParameter() {
		initSollTemperatur = T_SOLL;
		initIstTemperatur = T_IST;
		initKuehlTemperatur = T_KUEHL;
	}

	/**
	 * Liefere ein Array der Schlüssel Brennelementeliste Demonstriert die
	 * Wandlung einer Collection in ein Array und ist auch nur deshalb hier so
	 * implementiert.
	 */
	public String[] getBeKeys() {
		int beSize = beListe.size(); // Anzahl der Elemente
		// Extrahiere die Keys in eine Liste
		Collection<String> keyList = beListe.keySet();
		String[] keyArray = new String[beSize];
		keyArray = keyList.toArray(keyArray);
		// keyArray = keyList.toArray(keyArray);
		return keyArray;
	}

	/**
	 * Lese eine Liste der verfügbaren Brennelemente und speichere sie in einer
	 * HashMap. Das Beladen des Ofens erfolgt aus dieser HashMap. Die Keys der
	 * Map enthalten die Brennelementekennung (String). Die Values enthalten
	 * einen String mit den Teilen:<br>
	 * <ul>
	 * <li>vollquallifizierter Klassenname</li>
	 * <li>Brennelementetyp</li>
	 * </ul>
	 * die durch das Zeichen ';' getrennt sind. Z.Z. noch fest codiert.
	 * 
	 * @return eine HashMap mit Namen und Typ der Brennelemente
	 */
	protected Map<String, String> leseBrennelementeListe() {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Holz", "de.integrata.hkw03.brennelemente.Holz; Buche");
		hm.put("Holz2", "de.integrata.hkw03.brennelemente.Holz; Fichte");
		hm.put("Holz3", "de.integrata.hkw03.brennelemente.Holz; Fichte");
		hm.put("Holz4", "de.integrata.hkw03.brennelemente.Holz; Buche");
		hm.put("Papier", "de.integrata.hkw03.brennelemente.Papier; Zeitung");
		hm.put("Sofa", "de.integrata.hkw03.moebel.Sofa; Stoff");
		hm.put("Dose1", "de.integrata.hkw03.brennelemente.Dose; Aluminium");
		//hm.put("Schrank", "de.integrata.hkw03.moebel.MetallSchrank; Blech");
		//hm.put("Sofa2", "de.integrata.hkw03.moebel.Sofa"); // nicht erzeugbar
		return hm;
	}

	/**
	 * Verbrennt ein Brennelementobjekt, das über den Schlüssel-Parameter der
	 * Brennelementeliste bestimmt wird. Das Objekt wird dynamisch mittels
	 * Factory erzeugt.
	 * 
	 * @param beKey
	 *            Schlüssel des Brennelements
	 */
	public void verbrennen(String beKey) {
		String beInfo = beListe.get(beKey);
		System.out.println("Belade " + beKey + "=" + beInfo);
		Object obj = createObject(beInfo);
		ofen.beladen(obj);
		obj = null;
		System.gc();
	}

	/**
	 * Erzeuge ein Brennelement-Objekt mittels ObjectFactory
	 * 
	 * @param pBrennelementInfo
	 *            Klassenname des Brennelements oder Klassenname und Typ durch
	 *            Zeichen ';' getrennt. (siehe auch Hashmap Brennelementliste)
	 * @return ein Brennelement-Objekt
	 */
	protected Object createObject(String pBrennelementInfo) {
		StringTokenizer tok = new StringTokenizer(pBrennelementInfo, ";");
		String className;
		String type;
		Object o = null;

		if (tok.countTokens() == 1) {
			className = pBrennelementInfo;
			// Erzeuge ein Brennelementobjekt mittels Klassenname
			o = ObjectFactoryImpl.getInstance().create(className);
		} else {
			className = tok.nextToken();
			type = tok.nextToken().trim();
			// Erzeuge ein Brennelementobjekt mittels Klassenname und
			// Brennelement-Typ
			o = ObjectFactoryImpl.getInstance().create(className, type);
		}
		return o;
	}
}
