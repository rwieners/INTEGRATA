package de.integrata.hkw05.gui;

import de.integrata.hkw05.ofen.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * GUI-Frame f�r Heizkraftwerk
 *
 * Der Frame enth�lt ein MasterPanel mp mit BorderLayout Im Norden steht der
 * Name des Kunden im S�den die aktuelle Zeit in Form des DateLabels. Der Name
 * des Kunden wird mit einem gro�en Font dargestellt.
 *
 * Im Center ist das EingabePanel mit GridLayout Name und Passwort werden vom
 * Kunden eingegeben Mit come und go-Kn�pfe werden sp�ter Aktionen ausgel�st
 *
 * Es wird eine Feuerliste verwendet, in der sich alle brennbaren Elemente
 * befinden. Die Feuerliste wird in einem Choice-Control dargestellt.
 *
 * Durch come wird u.a. die Choice mit den Brennbaren Elementen aktiviert. Durch
 * den Knopf burn wird das selektierte Element verbrennt. Die Events des Knopfes
 * burns werden an den Listener BurnListener als innere Klasse delegiert. Der
 * Controller ruft die Methode load auf mit dem selktierten Element.
 *
 * Am Ende des Gui-Aufbaus in init() wird der Fokus f�r das erste Eingabefeld
 * (Name) angefordert.
 * 
 *
 * @version 1.3.0615 krg Applet-Code entfernt
 */

public class OfenGUI extends Frame {
	private static final long serialVersionUID = 1L;

	// GUI-Elemente
	private Label labelKunde, labelName, labelPassword;

	private TextField name, password;

	private Button come, go, burn;

	private Choice choiceBrennbar; // alternativ swing.JComboBox

	private static String txtTitle = "Heizkraftwerk INTEGRATA Stufe 1.5.1115";

	private static Frame frame = null;

	private MenuItem miNew, miExit, miAbout;

	// Verbindung zum Ofen
	private ConfigurableOfenController oc;

	// -----------------------------------------------------------------------
	/**
	 * Erzeuge den Frame im Konstruktor
	 */
	public static void main(String[] args) {
		frame = new OfenGUI();
	}

	// -----------------------------------------------------------------------
	// OfenGUI Konstruktor
	public OfenGUI() {
		System.out.println("OfenGUI Konstruktor");
		oc = new ConfigurableOfenController();
		frame = new Frame(this.getClass().getName());
		frame.setMenuBar(createMenu());
		// Das �u�ere FlowLayout ist n�tig, damit sich das Formular
		// in der Gr��e nicht �ndert beim resizen
		frame.setLayout(new FlowLayout());
		frame.setBackground(Color.DARK_GRAY);
		Panel mp = this.init();
		frame.add(mp);
		frame.pack();
		
		this.setInsets(mp);
		//frame.setResizable(false);

		frame.setVisible(true);
		System.out.println(frame.getSize());
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) { System.exit(0); }});
    	
	} // OfenGUI()

	/**
 * 
 */
	private void setInsets(Panel mp) {
		Dimension dimFrame = frame.getSize();
		System.out.println("Frame-Size=" + dimFrame);
		Dimension dimPanel = mp.getSize();
		System.out.println("Panel-Size=" + dimPanel);
		int newHeight = dimFrame.height + 20;
		int newWidth = dimFrame.width + 20;
		frame.setMinimumSize(new Dimension(newHeight, newHeight));
		frame.doLayout();
	}

	/**
	 * Die init()-Methode erzeugt die GUI-Elemente
	 */
	public Panel init() {
		System.out.println("OfenGUI.init");

		// In das MasterPanel kommen Titel(North), Formular(Center)
		// und Uhrzeit(South)
		Panel mp = new Panel();
		mp.setBackground(Color.decode("#972d00"));
		mp.setLayout(new BorderLayout());

		labelKunde = new Label(txtTitle, Label.CENTER);
		labelKunde.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		labelKunde.setForeground(Color.YELLOW);

		// Das Formular erzeugen
		Panel ep = createEingabePanel();

		// Die Uhr sp�ter als Thread realisieren
		Date dl = new Date();
		Label lblDate = new Label(dl.toString());
		lblDate.setForeground(Color.YELLOW);

		// Die GUI-Elemente im BorderLayout anordnen
		mp.add(labelKunde, BorderLayout.NORTH);
		mp.add(ep, BorderLayout.CENTER);
		mp.add(lblDate, BorderLayout.SOUTH);
		return mp;
	} // init()

	// -----------------------------------------------------------------------
	/**
	 * Das Eingabe-Panel als eigenes Modul um es auch noch anderweitig verwenden
	 * zu k�nnen. Bei dieser Erweiterung, sollte es aber besser als eigene Klasse
	 * "EingabePanel extends Panel" realisiert werden.
	 */
	private Panel createEingabePanel() {

		Panel ep = new Panel();

		labelName = new Label(" Identifikation :", Label.RIGHT);
		labelName.setForeground(Color.YELLOW);
		labelPassword = new Label(" Kennwort :", Label.RIGHT);
		labelPassword.setForeground(Color.YELLOW);
		name = new TextField(10);

		password = new TextField(8);
		password.setEchoChar('*'); // Sterne f�r Passwort-Eingabe

		come = new Button("kommen");
		go = new Button("gehen");
		go.setEnabled(false);
		burn = new Button("beladen");
		burn.setEnabled(false);

		choiceBrennbar = new Choice();

		String[] feuerliste = oc.getBeKeys();
		for (int i = 0; i < feuerliste.length; i++) {
			Object item = feuerliste[i];
			System.out.println("item=" + item);
			choiceBrennbar.addItem(feuerliste[i]);
		}

		choiceBrennbar.setEnabled(false);
		come.setForeground(Color.blue);
		go.setForeground(Color.blue);

		// Wir wollen ein zellenf�rmiges Layout
		ep.setLayout(new GridLayout(7, 2, 10, 10));

		ep.add(new Label());
		ep.add(new Label()); // Abstand 2 dummies
		ep.add(labelName);
		ep.add(name);
		ep.add(labelPassword);
		ep.add(password);
		ep.add(come);
		ep.add(go);
		ep.add(new Label());
		ep.add(new Label()); // Abstand 2 dummies
		ep.add(choiceBrennbar);
		ep.add(burn);

		// Der Kunde soll gleich loslegen k�nnen
		name.requestFocus();

		return ep;
	}

	// Optional ein Men�
	private MenuBar createMenu() {
		MenuBar mb = new MenuBar();
		Menu mFile = new Menu("File");
		miNew = new MenuItem("New");
		miExit = new MenuItem("Exit");
		mFile.add(miNew);
		mFile.add(miExit);
		miExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Menu mHelp = new Menu("Help");
		miAbout = new MenuItem("About");
		miAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		mHelp.add(miAbout);

		mb.add(mFile);
		mb.add(mHelp);
		return mb;
	}

	// Optional ein About-Dialog
	private void showAboutDialog() {
		Dialog adlg = new Dialog(frame);
		String title = "�ber Heitzkraftwerk Integrata";
		String info = "Version 1.3.0615 Applet-Code entfernt";
		JOptionPane.showMessageDialog(frame, info, title,
				JOptionPane.INFORMATION_MESSAGE);
		// oder
		adlg.setTitle(title);
		adlg.setSize(300, 200);
		adlg.setLocation(this.burn.getLocation());
		adlg.setLayout(new FlowLayout());
		adlg.add(new Label(info));

		adlg.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		adlg.setVisible(true);
	}

}
