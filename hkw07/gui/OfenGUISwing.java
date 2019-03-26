package de.integrata.hkw07.gui;

import de.integrata.hkw07.ofen.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

/**
 * GUI für Heizkraftwerk
 *
 * Das Applet enthält ein MasterPanel mp mit BorderLayout Im Norden steht der
 * Name des Kunden im Süden die aktuelle Zeit in Form des DateLabels. Der Name
 * des Kunden wird mit einem großen Font dargestellt.
 *
 * Im Center ist das EingabePanel mit GridLayout Name und Passwort werden vom
 * Kunden eingegeben Mit come und go-Knöpfe werden Aktionen ausgelöst Die Events
 * werden an die jeweiligen inneren Listener-Klassen ComeListener und GoListener
 * delegiert.
 *
 * Es wird eine Feuerliste verwendet, in der sich alle brennbaren Elemente
 * befinden. Die Feuerliste wird in einem Choice-Control dargestellt.
 *
 * Durch come wird u.a. die Choice mit den Brennbaren Elementen aktiviert. Durch
 * den Knopf burn wird das selektierte Element verbrennt. Die Events des Knopfes
 * burns werden an den Listener BurnListener als innere Klasse delegiert. Der
 * Controller ruft die Methode load auf mit dem selktierten Element.
 *
 * Am Ende des Gui-Aufbaus in init() wird der Fokus für das erste Eingabefeld
 * (Name) angefordert.
 * 
 * Das Applet soll auch eine main-Methode besitzen, so daß es auch als
 * StandAlone funktioniert, hierzu erzeugen wir einen JFrame(Container) und
 * fügen eine Instanz des Applets(Component) hinzu. Dann simulieren wir die
 * Lebenszyklen durch den Browser
 *
 * @author Integrata Training AG
 * @version 1.2 krg 2.3.01
 */

public class OfenGUISwing extends JPanel {
	private static final long serialVersionUID = 1L;

	// GUI-Elemente
	private JLabel labelKunde, labelName, labelPassword;

	private JTextField name;

	private JPasswordField password;

	private JButton come, go, burn;

	private JComboBox<String> choiceBrennbar; // alt. JComboBox

	private static String txtTitle = "Heizkraftwerk Integrata Swing V7 - MVC";

	private ConfigurableOfenController oc;

	// Frames
	private static JFrame frame = null;

	private static JPanel mpanel;

	// -----------------------------------------------------------------------
	/**
	 */
	public static void main(String[] args) {
		mpanel = new OfenGUISwing();
		frame = new JFrame(mpanel.getClass().getName());
		frame.getContentPane().add(mpanel);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
		centerFrame(frame);
		
		/**
		 * anonyme innere Klasse als WindowListener, schliesst die Anwendung
		 */
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			} // windowClosing()
		});
	}

	/**
	 * Ausrichtung des übergebenen Frames in der Bildschirmmitte.
	 * 
	 * @param frame
	 */
	private static void centerFrame(Frame frame) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension size = tk.getScreenSize();
		int sizeX = (int) size.getWidth();
		int sizeY = (int) size.getHeight();
		System.out.println("sizeX=" + sizeX + ", sizeY=" + sizeY);
		Dimension frameSize = frame.getSize();
		int fsizeX = (int) frameSize.getWidth();
		int fsizeY = (int) frameSize.getHeight();
		frame.setLocation((sizeX / 2) - (fsizeX / 2), (sizeY / 2) - (fsizeY / 2));
	}

	// -----------------------------------------------------------------------
	// OfenGUI Konstruktor, erzeugt die GUI-Elemente 
	public OfenGUISwing() {
		System.out.println("OfenGUI Konstruktor");
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
		// ep.setBorder(BorderFactory.createLineBorder(Color.black));
		ep.setBackground(new Color(230, 230, 240));

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

		// Digitale Temperaturanzeige starten
		TempDisplay td = new TempDisplay(oc.getOfen());
		JPanel gd = new GraphDisplay();
		oc.getOfen().addObserver((Observer) gd);
	}


	// -----------------------------------------------------------------------
	/**
	 * Das Eingabe-JPanel als eigenes Modul um es auch noch anderweitig verwenden
	 * zu können. Bei dieser Erweiterung, sollte es aber gleich als eigene Klasse
	 * realisiert werden. (-->src051)
	 */
	private JPanel createEingabePanel() {

		JPanel ep = new JPanel();

		labelName = new JLabel(" Identifikation :", JLabel.RIGHT);
		labelPassword = new JLabel(" Kennwort :", JLabel.RIGHT);
		name = new JTextField(10);

		password = new JPasswordField(8);
		password.setEchoChar('*'); // Sterne für Passwort

		come = new JButton("kommen");
		go = new JButton("gehen");
		go.setEnabled(false);
		burn = new JButton("beladen");
		burn.setEnabled(false);

		choiceBrennbar = new JComboBox<String>();
		String[] feuerliste = oc.getBeKeys();
		for (int i = 0; i < feuerliste.length; i++) {
			System.out.println(feuerliste[i]);
			choiceBrennbar.addItem(feuerliste[i]);
		}

		choiceBrennbar.setEnabled(false);
		come.setForeground(Color.blue);
		go.setForeground(Color.blue);

		// Wir wollen ein zellenförmiges Layout
		ep.setLayout(new GridLayout(7, 2, 10, 10));

		ep.add(new JLabel());
		ep.add(new JLabel()); // Abstand 2 dummies
		ep.add(labelName);
		ep.add(name);
		ep.add(labelPassword);
		ep.add(password);
		ep.add(come);
		ep.add(go);
		ep.add(new JLabel());
		ep.add(new JLabel()); // Abstand 2 dummies
		ep.add(choiceBrennbar);
		ep.add(burn);

		// Der Kunde soll gleich loslegen können
		name.requestFocus();

		/*
		 * EventHandler registrieren
		 */
		come.addActionListener(new ComeListener());
		go.addActionListener(new GoListener());
		burn.addActionListener(new BurnListener());
		return ep;
	}

	// -----------------------------------------------------------------------
	// Dynamischer Teil der GUI: die Event-Handler (hkw06) ------------------
	// -----------------------------------------------------------------------
	/**
	 * ActionEvent: Button come Jemand meldet sich an der GUI an, indem er den
	 * kommen-Knopf drückt. Der ComeListener aktiviert die anderen Knöpfe und
	 * deaktiviert sich selbst. Die Uhrzeit der Anmeldung wird später
	 * protokolliert.
	 */
	private class ComeListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			/*
			 * Name und Kennwort überprüfen
			 */
			String username = name.getText();
			char[] pwdChars = password.getPassword();
			String pwd = "";
			for (int i = 0; i < pwdChars.length; i++)
				pwd += pwdChars[i];
			System.out.println("user=" + username + ", pwd=" + pwd);
			if ((pwd.length() > 0) && (username.equals(pwd) == false)) {
				System.out.println("ComeListener(): falsche user/password Eingabe");
				name.selectAll();
				name.requestFocus();
				password.setText(""); // password Feld löschen
				JOptionPane.showMessageDialog(frame,
						"Sie haben einen falschen Benutzername oder "
								+ "ein falsches Kennwort eingegeben");
				return;
			}
			name.setText(""); // username löschen
			password.setText(""); // pasword Feld löschen
			come.setEnabled(false);
			go.setEnabled(true);
			burn.setEnabled(true);
			choiceBrennbar.setEnabled(true);
		}
	}

	/**
	 * ActionEvent: Button go Jemand meldet sich an der GUI ab, indem er den
	 * gehen-Knopf drückt. Der GoListener aktiviert den kommen-Knopf und
	 * deaktiviert sich selbst. Die Uhrzeit der Anmeldung wird später
	 * protokolliert.
	 */
	private class GoListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			come.setEnabled(true);
			go.setEnabled(false);
			burn.setEnabled(false);
			choiceBrennbar.setEnabled(false);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * ActionEvent: Button burn Der BurnListener übergibt das gewählte
	 * Brennelement aus der GUI an die load Methode als String zusammen mit der
	 * Uhrzeit.
	 */
	private class BurnListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String brennelement = (String) choiceBrennbar.getSelectedItem();
			oc.verbrennen(brennelement);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * TextEvent: bei Änderung in den zwei Textfeldern Der DelimiterListener sorgt
	 * dafür, dass bei einem TextFeld nur eine maximale Anzahl Zeichen genutzt
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
			System.out.println(line);
		}
	}

}
