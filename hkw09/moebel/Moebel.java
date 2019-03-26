package de.integrata.hkw09.moebel;

/**
 * Moebel ist eine abstrakte Basisklasse, die nicht
 * instantiiert werden kann
 *
 * @version 1.1 krg 1.5.2003 (hkw02)
 */
abstract public class Moebel {

  protected String typ;

  public Moebel(String typ) {
    this.typ = typ;
  } // Moebel()

}
