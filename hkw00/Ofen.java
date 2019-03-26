package de.integrata.hkw00;

/**
 * Der Ofen beschreibt das Modell unser Heizkraftwerksanwendung, das heisst,
 * hier ist der Gesch�ftsprozess abgebildet, der Ofen wird beladen,
 * Brennelemente werden verbrannt und erh�hen die Temperatur des Ofens. Wird der
 * Ofen zu heiss wird automatisch gek�hlt.
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
	 * Der Ofen ist lediglich der Beh�lter f�r die Brennelemente und die
	 * Brennkammer, die Erw�rmung von Wasser und Erzeugung des Stroms �ber
	 * Turbinen verbleibt als �bung :-)
	 * 
	 */
	public Ofen(int sollTemperatur) {
		this.sollTemperatur = sollTemperatur;
		bk = new Brennkammer(this);
	}

	/*
	 * Getter und Setter f�r Properties
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
	public void erh�heTemperatur(int brennwert) {
		setIstTemperatur(this.istTemperatur + brennwert);
	}

	private void k�hlen() {
		setIstTemperatur(istTemperatur - kuehlTemperatur);
	}

	/**
	 * In den Ofen k�nnen beliebige Objekte gegeben werden. Die Elemente, die
	 * nicht Brennbar sind f�hren in der ersten Fassung zum Programmabbruch
	 */
	public void beladen(Object o) {
		if (o == null) {
			System.out.println("null-Objekt hat keinen Heizwert");
		} else {
				bk.verbrennen((Holz)o);
		}
	}

}
