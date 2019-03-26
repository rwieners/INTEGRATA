package de.integrata.hkw03.global;

/**
 * Skalierbarkeit:
 * Das Interface Brennbar ist die Schnittstille zwischen Ofen-Bauer
 * und Brennelemente-Hersteller, schließlich soll der Generalarchitekt
 * des Heizkraftwerks verschiedene Öfen und Brennelemte verwenden können.
 *
 * @version 2.6.107 krg Brennbare Objekte können nach Brennwert sortiert werden.
 */
public interface Brennbar extends Comparable<Brennbar> {
	public int brennen();
	public int getBrennwert();
}
