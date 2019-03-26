package de.integrata.hkw02.util;

import java.lang.reflect.Constructor;



/**
 * Interface für eine ObjectFactory. Es sind zwei create()-Methoden vorgesehen,
 * eine zur Erzeugung von Objekten mit dem parameterlosen Konstruktor,
 * eine mit einem String als Parameter.
 * <p>
 * Die Factory ist als Singleton ausgelegt
 */
@SuppressWarnings("rawtypes")
public class ObjectFactoryImpl implements ObjectFactory {
  /**
   * Singleton-Instanz
   */
  private static ObjectFactoryImpl instance;
  
  /**
   * privater Konstruktor: Singleton-Pattern
   */
  private ObjectFactoryImpl() {
  }
  
  /**
   *  Liefert die Referenz auf das Singleton zurück
   */
  public static ObjectFactoryImpl getInstance() {
    if (instance == null) {
      instance = new ObjectFactoryImpl();  
    }
    return instance;
  }
  
  /**  
   * Konstruktion der Klasse, deren voll qualifizierter Klassenname als Parameter übergeben wird. <p>
    Als Parameter bei der Konstruktion ist ein String vorgesehen<p>
    Falls irgendetwas schief geht, wird ein allgemeines java.lang.Object zurückgegeben<p>
    @param pClassName Voll qualifizierter Klassenname
    @param pParameter String, der dem Konstruktor übergeben wird
    @return die Instanzierte Klasse oder ein java.lang.Object
  */  
  public Object create(String pClassName){
    if (pClassName == null){
     return null; 
    }
    try {
    	Class c = Class.forName(pClassName);
    	Object o = c.newInstance();
      return o;
    }  
    catch(Exception e) {
      System.out.println("ObjectFactoryIpml: pClassName=" + pClassName
      		+ e);  
      return null;
    }
  }
  
  
  /**
    Konstruktion der Klasse, deren voll qualifizierter Klassenname als Parameter übergeben wird. <p>
    Als Parameter bei der Konstruktion ist ein String vorgesehen<p>
    Falls irgendetwas schief geht, wird ein allgemeines java.lang.Object zurückgegeben<p>
    @param pClassName Voll qualifizierter Klassenname
    @param pParameter String, der dem Konstruktor übergeben wird
    @return die Instanzierte Klasse oder ein java.lang.Object
  */
  public Object create(String pClassName, String pParameter){
    Class[] paramTypes = {String.class};
    Object[] paramValues = {pParameter};
    try {
    	Class c = Class.forName(pClassName);
    	Constructor con = c.getConstructor(paramTypes);
    	Object o = con.newInstance(paramValues);
      return o;
    }
    catch(Exception e) {
      System.out.println("ObjectFactoryIpml: pClassName=" + pClassName
      		+ ", EType=" + e);  
      return null;
    }
  }
}
