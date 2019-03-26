package de.integrata.hkw09.gui;

import de.integrata.hkw09.ofen.*;

import java.awt.*;
import java.awt.event.*;
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
 * @author Integrata Training AG
 * @version 1.2 krg 2.3.01
 * @version 1.3.0615 krg Applet-Code entfernt
 */

public class OfenGUI extends Frame {
	private static final long serialVersionUID = 1L;

	// GUI-Elemente
	private Label labelKunde, labelName, labelPassword;

	private TextField name, password;

	private Button come, go, burn;

	private Choice choiceBrennbar; // alternativ swing.JComboBox

	private static String txtTitle = "Heizkraftwerk INTEGRATA Stufe 1.9.1115";

	private static Frame frame = null;

	private MenuItem miNew, miExit, miAbout;

	// Verbindung zum Ofen
	private ConfigurableOfenController oc;

	// DAO-Klasse
	private DbAccess dba;

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
		// frame.setResizable(false);

		frame.setVisible(true);
		System.out.println(frame.getSize());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
				dba.dbClose();
			}
		});

		// Digitale Temperaturanzeige starten
		TempDisplay td = new TempDisplay(oc.getOfen());

		// Datenbankverbindung herstellen
		dba = new DbAccess();

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
		// Date dl = new Date();
		// Label lblDate = new Label(dl.toString());
		// Die aktuelle Zeit wird durch eine eigene Label-Komponente angezeigt.
		// Durch Implementierung des Interface Runnable wird die Uhrzeit
		// sekundengenau
		// aktuallisiert
		DateLabel lblDate2 = new DateLabel();
		lblDate2.setForeground(Color.YELLOW);
		lblDate2.setFont(new Font("Arial", Font.ITALIC, 16));

		// Die GUI-Elemente im BorderLayout anordnen
		mp.add(labelKunde, BorderLayout.NORTH);
		mp.add(ep, BorderLayout.CENTER);
		mp.add(lblDate2, BorderLayout.SOUTH);
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

		/*
		 * EventHandler registrieren
		 */
		come.addActionListener(new ComeListener());
		go.addActionListener(new GoListener());
		burn.addActionListener(new BurnListener());
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
		String title = "�ber Heizkraftwerk Integrata";
		String info = "Version 1.7.1115 Applet-Code entfernt";
		JOptionPane.showMessageDialog(frame, info, title,
				JOptionPane.INFORMATION_MESSAGE);
		// odereigener Dialog
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

	// -----------------------------------------------------------------------
	// Dynamischer Teil der GUI: die Event-Handler --------------------------
	// -----------------------------------------------------------------------
	/**
	 * ActionEvent: Button come Jemand meldet sich an der GUI an, indem er den
	 * kommen-Knopf dr�ckt. Der ComeListener aktiviert die anderen Kn�pfe und
	 * deaktiviert sich selbst. Die Uhrzeit der Anmeldung wird sp�ter
	 * protokolliert.
	 */
	private class ComeListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			/*
			 * Name und Kennwort �berpr�fen
			 */
			String username = name.getText();
			String pwd = password.getText();
			// System.out.println("Check user=" + username + ", pwd=" + pwd);
			if (dba.dbCheckUser(username, pwd) != true) {
				System.out.println("ComeListener(): falsche user/password Eingabe");
				// Hier sollte ein Fehler-Dialogfenster angezeigt werden!
				name.selectAll();
				name.requestFocus();
				JOptionPane.showMessageDialog(frame,
						"Sie haben einen falschen Benutzernamen oder "
								+ "ein falsches Kennwort eingegeben",
								"Fehler in Benutzereingabe", JOptionPane.WARNING_MESSAGE);
				return;
			}

			come.setEnabled(false);
			go.setEnabled(true);
			burn.setEnabled(true);
			choiceBrennbar.setEnabled(true);
			dba.dbAktion(username, "kommen", new java.util.Date());
		}
	}

	/**
	 * ActionEvent: Button go Jemand meldet sich an der GUI ab, indem er den
	 * gehen-Knopf dr�ckt. Der GoListener aktiviert den kommen-Knopf und
	 * deaktiviert sich selbst. Die Uhrzeit der Anmeldung wird sp�ter
	 * protokolliert.
	 */
	private class GoListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			come.setEnabled(true);
			go.setEnabled(false);
			burn.setEnabled(false);
			choiceBrennbar.setEnabled(false);
			dba.dbAktion(name.getText(), "gehen", new java.util.Date());
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * ActionEvent: Button burn Der BurnListener �bergibt das gew�hlte
	 * Brennelement aus der GUI an die load Methode als String zusammen mit der
	 * Uhrzeit.
	 */
	private class BurnListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String brennelement = choiceBrennbar.getSelectedItem();
			oc.verbrennen(brennelement);
      dba.dbAktion(name.getText(),"verbrenne " + brennelement, new java.util.Date());
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * TextEvent: bei �nderung in den zwei Textfeldern Der DelimiterListener sorgt
	 * daf�r, dass bei einem TextFeld nur eine maximale Anzahl Zeichen genutzt
	 * wird.
	 */
	private class DelimitorListener implements TextListener {
		private int limit;

		public DelimitorListener(int value) {
			this.limit = value;
		}

		public void textValueChanged(TextEvent te) {
			TextComponent tc = (TextComponent) te.getSource();
			String line = tc.getText();

			int pos = tc.getCaretPosition();
			if (line.length() > limit) {
				tc.setText(line.substring(0, limit));
				tc.setCaretPosition(pos);
			}
			// System.out.println(line);
		}
	}

	// -----------------------------------------Ende Inner Classes
}
