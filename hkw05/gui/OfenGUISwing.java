package de.integrata.hkw05.gui;

import de.integrata.hkw05.ofen.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


/**
 * Applet für Heizkraftwerk
 *
 * Das Applet enthält ein MasterPanel mp mit BorderLayout
 * Im Norden steht der Name des Kunden im Süden die aktuelle
 * Zeit in Form des DateLabels. Der Name des Kunden wird mit
 * einem großen Font dargestellt.
 *
 * Im Center ist das EingabePanel mit GridLayout
 * Name und Passwort werden vom Kunden eingegeben
 * Mit come und go-Knöpfe werden Aktionen ausgelöst
 * Die Events werden an die jeweiligen inneren Listener-Klassen
 * ComeListener und GoListener delegiert.
 *
 * Es wird eine Feuerliste verwendet, in der sich alle brennbaren
 * Elemente befinden. Die Feuerliste wird in einem Choice-Control dargestellt.
 *
 * Durch come wird u.a. die Choice mit den Brennbaren Elementen
 * aktiviert. Durch den Knopf burn wird das selektierte Element
 * verbrennt. Die Events des Knopfes burns werden an den
 * Listener BurnListener als innere Klasse delegiert.
 * Der Controller ruft die Methode load auf mit dem selktierten
 * Element.
 *
 * Am Ende des Gui-Aufbaus in init() wird der Fokus für das
 * erste Eingabefeld (Name) angefordert.
 * 
 * Das Applet soll auch eine main-Methode besitzen, so daß es
 * auch als StandAlone funktioniert, hierzu erzeugen wir einen
 * JFrame(Container) und fügen eine Instanz des Applets(Component)
 * hinzu. Dann simulieren wir die Lebenszyklen durch den Browser
 *
 * @author kreutzersoft.de
 * @version 1.2  krg 2.3.01		 
 */

public class OfenGUISwing extends Applet {
	private static final long serialVersionUID = 1L;

	// GUI-Elemente 
	private JLabel labelKunde, labelName, labelPassword;
	private JTextField name, password;
	private JButton come, go, burn;
	private JComboBox<String> choiceBrennbar; // alt. JComboBox
	private static String txtTitle = "Heizkraftwerk INTEGRATA Stufe 5-Swing";
	private ConfigurableOfenController oc;
	 
		
	// Frames
	private static JFrame frame = null;
	private static Applet applet;	

	//-----------------------------------------------------------------------
	/**
	 * Dual Personality von Applet und Applikation. Hier wird ein Teil
	 * des Browsers simuliert, der Applets darstellen kann.
	 * Ist das Attribut frame!=null, so handelt es sich um eine
	 * standalone Applikation, ansonsten um ein Applet
	 */ 
	public static void main(String[] args) {
		applet = new OfenGUISwing();
		frame = new JFrame(applet.getClass().getName());
		applet.init();
		applet.start();
		frame.getContentPane().add(applet);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
		
		/**
		 * anonyme innere Klasse als WindowListener, 
		 * schliesst die Anwendung
		 */
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				applet.stop();
				applet.destroy();
				System.exit(0);
			} // windowClosing()
		});
	}


	//-----------------------------------------------------------------------
	// OfenGUI Konstruktor (wird nicht benötigt)
	public OfenGUISwing() {
		System.out.println("OfenGUI Konstruktor");
	} // OfenGUI()
	
	
	/**
	 * Die init()-Methode erzeugt die GUI-Elemente
	 */
	public void init()  {
		System.out.println("OfenGUI.init");
		oc = new ConfigurableOfenController();
		setBackground(Color.decode("#eeeeff"));
		
		// In das MasterPanel kommen Titel(North), Formular(Center)
		// und Uhrzeit(South) 
		JPanel mp = new JPanel();
		mp.setLayout(new BorderLayout());
		
		labelKunde = new JLabel(txtTitle, JLabel.CENTER);   
		labelKunde.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		// Das Formular erzeugen
		JPanel ep = createEingabePanel();
		
		// Die Uhr später als Thread realisieren
		Date dl = new Date();
		JLabel lblDate = new JLabel(dl.toString());
		
		// Die GUI-Elemente im BorderLayout anordnen
		mp.add(labelKunde, BorderLayout.NORTH);
		mp.add(ep, BorderLayout.CENTER);
		mp.add(lblDate, BorderLayout.SOUTH);
		
		// Das äußere FlowLayout ist nötig, damit sich das Formular
		// in der Größe nicht ändert beim resizen
		this.setLayout(new FlowLayout());
		add(mp);
	} // init()
	
	
	/** 
	 * Die start()-Methode des Applet-Frameworks
	 */
	public void start() {
		System.out.println("OfenGUI.start");
	}
	
	/** 
	 *  Die stop()-Methode des Applet-Frameworks
	 */ 
	public void stop() {
		System.out.println("OfenGUI.stop");
	}
	
	/** 
	 * Die destroy-Methode des Applet-Frameworks
	 **/
	public void destroy() {
		System.out.println("OfenGUI.destroy");
	}
	
	
	//-----------------------------------------------------------------------
	/**
	 * Das Eingabe-JPanel als eigenes Modul um es auch noch anderweitig
	 * verwenden zu können. Bei dieser Erweiterung, sollte es aber gleich
	 * als eigene Klasse realisiert werden. (-->src051)
	 */ 
	private JPanel createEingabePanel() {
		
		JPanel ep = new JPanel();
		
		labelName = new JLabel(" Identifikation :", JLabel.RIGHT);
		labelPassword = new JLabel (" Kennwort :", JLabel.RIGHT);
		name = new JTextField(10);
		
		password = new JTextField(8);
		//password.setEchoChar('*');				// Sterne für Passwort 
		
		come = new JButton("kommen");
		go = new JButton("gehen");
		go.setEnabled(false);
		burn = new JButton("beladen");
		burn.setEnabled(false);
		
		choiceBrennbar = new JComboBox<String>();
		String[] feuerliste = oc.getBeKeys();
		for (int i=0; i < feuerliste.length; i++) {
			System.out.println(feuerliste[i]);
			choiceBrennbar.addItem(feuerliste[i]);
		}
		
		choiceBrennbar.setEnabled(false);
		come.setForeground(Color.blue);
		go.setForeground(Color.blue);
		
		// Wir wollen ein zellenförmiges Layout
		ep.setLayout(new GridLayout(7, 2, 10, 10));
		
		ep.add(new JLabel()); ep.add(new JLabel());		// Abstand 2 dummies
		ep.add(labelName); ep.add(name); 
		ep.add(labelPassword); ep.add(password);
		ep.add(come); ep.add(go);
		ep.add(new JLabel()); ep.add(new JLabel());		// Abstand 2 dummies
		ep.add(choiceBrennbar);ep.add(burn);
		
		// Der Kunde soll gleich loslegen können
		name.requestFocus();
		
		return ep;
	}
	
}
