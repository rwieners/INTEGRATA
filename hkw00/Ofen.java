package de.integrata.hkw00;

/**
 * Der Ofen beschreibt das Modell unser Heizkraftwerksanwendung, das heisst,
 * hier ist der Geschäftsprozess abgebildet, der Ofen wird beladen,
 * Brennelemente werden verbrannt und erhöhen die Temperatur des Ofens. Wird der
 * Ofen zu heiss wird automatisch gekühlt.
 * 
 * 
 * @version 1.4 krg 8.3.2004 (hkw00)
 */
public class Ofen {

	private int istTemperatur = 20;
	private int sollTemperatur = 100;
	private int kuehlTemperatur = 30;
	private Brennkammer bk = null;

	/**
	 * Der Ofen ist lediglich der Behälter für die Brennelemente und die
	 * Brennkammer, die Erwärmung von Wasser und Erzeugung des Stroms über
	 * Turbinen verbleibt als Übung :-)
	 * 
	 */
	public Ofen(int sollTemperatur) {
		this.sollTemperatur = sollTemperatur;
		bk = new Brennkammer(this);
	}

	/*
	 * Getter und Setter für Properties
	 */
	public int getIstTemperatur() {
		return this.istTemperatur;
	}

	public int getSollTemperatur() {
		return this.sollTemperatur;
	}

	public int getKuehlTemperatur() {
		return this.kuehlTemperatur;
	}

	public void setSollTemperatur(int i) {
		this.sollTemperatur = i;
	}

	public void setKuehlTemperatur(int i) {
		this.kuehlTemperatur = i;
	}

	private void setIstTemperatur(int i) {
		this.istTemperatur = i;
	}

	/**
	 */
	public void erhöheTemperatur(int brennwert) {
		setIstTemperatur(this.istTemperatur + brennwert);
	}

	private void kühlen() {
		setIstTemperatur(istTemperatur - kuehlTemperatur);
	}

	/**
	 * In den Ofen können beliebige Objekte gegeben werden. Die Elemente, die
	 * nicht Brennbar sind führen in der ersten Fassung zum Programmabbruch
	 */
	public void beladen(Object o) {
		if (o == null) {
			System.out.println("null-Objekt hat keinen Heizwert");
		} else {
				bk.verbrennen((Holz)o);
		}
	}

}
