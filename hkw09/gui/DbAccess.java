package de.integrata.hkw09.gui;

import java.text.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * Diese Klasse stellt Methoden für die OfenGUI zur Verfügung um die 
 * Benutzeraktionen der User zu protokolieren und um eine Benutzer-
 * Autentifizierung durchzuführen.
 *
 * Die Aktionen KOMMEN, GEHEN und VERBRENNEN werden in der Datenbank
 * in der Tabelle OFENPROTOKOLL protokolliert.
 *
 * Bei der Aktion KOMMEN wird in der Datenbank-Tabelle PASSWORT für
 * den eingegebenen Namen das Kennwort überprüft.
 *
 * Es werden allgemeine Methoden für den Verbindungsaufbau
 * zur Datenbank und das Schliessen bereitgestellt.
 *
 * @version 2.6.107 krg hkw09
 */
public class DbAccess {
  
	//-----------------------------------------------------------------------
  // Default Datenbankzugriffs Attribute
  private String dbDriver = "com.mysql.jdbc.Driver";
  private String dbURL = "jdbc:mysql://localhost/ofen";
  private String dbUser = "root";
  private String dbPwd = "unilog";

	private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
	private Connection dbConn = null;  
	private Statement dbStmt = null;
	
	/**
   * Die Verbindung zur Datenbank wird erzeugt und ein Statement-Objekt
   * für die SQL-Kommandos bereitgestellt.
   */ 
  private void dbConnect() {
  	this.readDbParameter();
  	if (dbConn != null) {
      System.out.println("dbConnect() dbConn=" + dbConn);
      return;
  	}
  	
    try {
      System.out.println("DB-Treiber laden");
      Class.forName(dbDriver);
      
    	System.out.println("  getConnection(): URL=" + dbURL);
      dbConn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
      dbStmt = dbConn.createStatement();
    }
    catch (SQLException sqle) {
      System.out.println("dbConnect() SQLFehler: "+ sqle);
    }
    catch (Exception e) {
      System.out.println("dbConnect() DB-Treiber nicht ladbar: " + e);
    }
  }  


	/**
   * Die Verbindung zur Datenbank wird getrennt.
   */ 
  public void dbClose() {
    System.out.println("dbCose()");
    try {
      if (dbStmt != null) dbStmt.close();
      if (dbConn != null) dbConn.close();
    }
    catch(SQLException sqle) {
      System.out.println("dbClose() SQLFehler: "+ sqle);
    }
    finally {
    	if (dbConn != null) {
    		try {dbConn.close();} catch(Exception e) { /*ignore*/ };
    	}
    }
    dbConn = null;
  }  


  /**
   * Uhrzeit und Aktion (kommen, gehen, verbrennen) werden protokolliert.
   * Anschließend wird die Verbindung getrennt.
   */
  public void dbAktion(String name, String action, java.util.Date date) {
    System.out.println("dbAktion=" + action);
    if (dbConn == null) {
    	dbConnect();
  	}
    try {
      String sSQL = "Insert into ofenprotokoll values ('"
        + name + "','" + action + "','" + sdf.format(date) + "')";
      System.out.println(" executeUpdate() SQL=" + sSQL);
      if (dbStmt != null) {
      	dbStmt.executeUpdate(sSQL); 
      }
    }
    catch (SQLException sqle) {
      System.out.println("dbAktion() SQLFehler: "+ sqle);
      sqle.printStackTrace();
    }
    this.dbClose();
  }  


	/**
   * Überprüfung des Benutzer-Kennwortes.
   */ 
  public boolean dbCheckUser(String user, String pwd) {
  	boolean pwdOk = false;
  	ResultSet rs;
  	
    System.out.println("dbCheckUser(): user=" + user + ", pwd=" + pwd);
    try {
	    if (dbConn == null) {
	    	dbConnect();
	  	}
	  	if (dbStmt != null) {
	  		String sSQL = "select * from Mitarbeiter where user = '" + user
	  				+ "' AND Password = '" + pwd + "';";
	  		System.out.println("dbCheckUser(): query=" + sSQL);
	  		rs = dbStmt.executeQuery(sSQL);
	  		if (rs.next()) {		// user und pwd in Datenbank gefunden
	  			pwdOk = true;
	  		}
	  	}
    }
    catch(SQLException sqle) {
      System.out.println("dbCheckUser() SQLFehler: "+ sqle);
      sqle.printStackTrace();
    }
    this.dbClose();
    return pwdOk;
  }  
  
  /**
   * 
   */
  private void readDbParameter() {
  	final String PARAM_FILE = "DbParameter.properties";
  	InputStream is;

  	try {
			is = this.getClass().getResourceAsStream(PARAM_FILE);
			System.out.println("readDbParameter from " + PARAM_FILE + ": is=" + is);
			Properties props = new Properties();
			props.load(is);  
			is.close();
			props.list(System.out);
			System.out.println("------------------------");
			this.dbDriver = props.getProperty("dbDriver");
			this.dbURL = props.getProperty("dbURL");
			this.dbUser = props.getProperty("dbUser");
			this.dbPwd = props.getProperty("dbPwd");
		}
		catch(IOException pException){
			System.out.println("Error reading properties from " + PARAM_FILE + ": "
					+ pException.getMessage());  
		}
  }
  
  //==========================================================================
  // Test only
  public static void main(String[] args) {
  	System.out.println("-- main: Test DbAccess");
  	DbAccess dba = new DbAccess();
  	boolean res = dba.dbCheckUser("gerhard", "kreutzer");
  	System.out.println("-- main: dbCheckUser=" + res);
  	dba.dbAktion("gerhard", "testen", new java.util.Date());
  	System.out.println("-- main: End test ");
  }
}
