package de.integrata.hkw06.global;

/**
 * Skalierbarkeit:
 * Das Interface Brennbar ist die Schnittstille zwischen Ofen-Bauer
 * und Brennelemente-Hersteller, schlie�lich soll der Generalarchitekt
 * des Heizkraftwerks verschiedene �fen und Brennelemte verwenden k�nnen.
 *
 * @version 1.2 krg 2.3.2001
 */
public interface Brennbar extends java.io.Serializable {

  public int brennen();

}
