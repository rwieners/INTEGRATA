package de.integrata.hkw03.util;

import java.lang.reflect.*;

/**
 * Interface für eine ObjectFactory. Es sind zwei create()-Methoden vorgesehen,
 * eine zur Erzeugung von Objekten mit dem parameterlosen Konstruktor,
 * eine mit einem String als Parameter.
 * <p>
 * Die Factory ist als Singleton ausgelegt
 * 
 * @version 1.1    krg    1.5.03
 */
public class ObjectFactoryImpl implements ObjectFactory {

	/**
	 * Singleton-Instanz der Factory Implementierung
	 */
	private static ObjectFactoryImpl instance;

	/**
	 * privater Konstruktor: Singleton-Pattern
	 */
	private ObjectFactoryImpl() {
		System.out.println("ObjectFactory created");
	}

	/**
	 *  Static Methode liefert die Referenz auf das Singleton zurück
	 */
	public static ObjectFactoryImpl getInstance() {
		if (instance == null) {
			instance = new ObjectFactoryImpl();
		}
		return instance;
	}

	/**  
	 * Konstruktion der Klasse, deren voll qualifizierter Klassenname
	 * als Parameter übergeben wird.
	 * Als Parameter bei der Konstruktion ist ein String vorgesehen.
	 * Falls irgendetwas schief geht, wird ein allgemeines java.lang.Object
	 * zurückgegeben
	 * <p>
	 * @param pClassName Voll qualifizierter Klassenname
	 * @param pParameter String, der dem Konstruktor übergeben wird
	 * @return die Instanzierte Klasse oder ein java.lang.Object
	 */
	public Object create(String pClassName) {
		if (pClassName == null) {
			return null;
		}
		try {
			return Class.forName(pClassName).newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println(e + ": not found");
			return new Object();
		} catch (InstantiationException e) {
			System.out.println(e + " object cannot be instantiated");
			return new Object();
		} catch (IllegalAccessException e) {
			System.out.println(e + ": <init> is not visible");
			return new Object();
		}
	}

	/**
	 * Konstruktion der Klasse, deren voll qualifizierter Klassenname
	 * als Parameter übergeben wird.
	 * Als Parameter bei der Konstruktion ist ein String vorgesehen
	 * Falls irgendetwas schief geht, wird ein allgemeines java.lang.Object
	 * zurückgegeben
	 * <p>
	 * @param pClassName Voll qualifizierter Klassenname
	 * @param pParameter String, der dem Konstruktor übergeben wird
	 * @return die Instanzierte Klasse oder ein java.lang.Object
	 */
	public Object create(String pClassName, String value) {
		Class<?>[] paramTypes = { String.class };
		Object[] paramValues = { value };
		try {
			return Class.forName(pClassName).getConstructor(
				paramTypes).newInstance(
				paramValues);
		} catch (InvocationTargetException e) {
			System.out.println(
				e + " Exception during invocation: " + e.getTargetException());
			return new Object();
		} catch (ClassNotFoundException e) {
			System.out.println(e + " not found");
			return new Object();
		} catch (NoSuchMethodException e) {
			System.out.println(e + ": No constructor with parameter 'String' found");
			return new Object();
		} catch (InstantiationException e) {
			System.out.println(e + ": during <init>");
			return new Object();
		} catch (IllegalAccessException e) {
			System.out.println(e + ": <init> is not visible");
			return new Object();
		}
	}
}
