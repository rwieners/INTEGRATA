/*
 * Created on 28.07.2004
 * see also: core Java Technical Tipps July/2004 
 */
package de.integrata.hkw09.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import javax.swing.*;

import de.integrata.hkw09.ofen.*;

public class EingabePanel2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton bLogin, bLogoff, bFire;
	private JTextField tfUser;
	private JPasswordField tfPwd;
	private JComboBox<String> cbBrennelement;
    //private DbAccess dba = new DbAccess();	       // Datenbankverbindung
    private OfenControllerImpl oc;
    
	/** Shared insets for all Components */
	private static final Insets insets = new Insets(5, 20, 5, 20);

	public static void main(String args[]) {
		JFrame frame = new JFrame("GridBagLayout Demo 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = frame.getContentPane();

		JPanel ep = new EingabePanel2(new ConfigurableOfenController());
		ep.setBorder(BorderFactory.createLineBorder(Color.black));
		ep.setBackground(new Color(200, 222, 222));
		JPanel mp = new JPanel();
		mp.add(ep);

		cp.add(mp);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * 
	 *
	 */
	public EingabePanel2(OfenControllerImpl oc) {
		this.oc = oc;
		this.setLayout(new GridBagLayout());

		gbadd(new JLabel(""), 0, 0);
		gbadd(new JLabel(""), 0, 1);
		
		JLabel lUser = new JLabel("Benutzername");
		gbadd(lUser, 1, 0);
		tfUser = new JTextField(15);
		gbadd(tfUser, 1, 1);

		JLabel lPwd = new JLabel("Kennwort");
		gbadd(lPwd, 2, 0);
		tfPwd = new JPasswordField(15);
		gbadd(tfPwd, 2, 1);

		bLogin = new JButton("Anmelden");
		gbadd(bLogin, 3, 0);
		bLogoff = new JButton("Abmelden");
		bLogoff.setEnabled(false);
		gbadd(bLogoff, 3, 1);

		gbadd(new JLabel(""), 4, 0);
		gbadd(new JLabel(""), 4, 1);

		cbBrennelement = new JComboBox<String>();
		cbBrennelement.setEnabled(false);
		String[] feuerliste = oc.getBeKeys();
		for (int i=0; i < feuerliste.length; i++) {
			System.out.println(feuerliste[i]);
			cbBrennelement.addItem(feuerliste[i]);
		}
		gbadd(cbBrennelement, 5, 0);
		bFire = new JButton("Ofen beladen");
		bFire.setEnabled(false);
		gbadd(bFire, 5, 1);

		gbadd(new JLabel(""), 6, 0);
		gbadd(new JLabel(""), 6, 1);

		/*
         * EventHandler registrieren
         */
        bLogin.addActionListener(new ComeListener());
        bLogoff.addActionListener(new GoListener());
        bFire.addActionListener(new BurnListener());
	}

	private void gbadd(Component c, int y, int x) {
		int anchor;
		if (x == 0)
			anchor = GridBagConstraints.NORTHWEST;
		else
			anchor = GridBagConstraints.NORTHEAST;

		GridBagConstraints constraints = new GridBagConstraints(x, y, // gridx,
																		// gridy
				1, 1, // gridwidth, gridheight
				1.0, 1.0, // weightx, weighty
				anchor,
				// GridBagConstraints.BOTH, // fill
				GridBagConstraints.NONE, // fill
				// GridBagConstraints.VERTICAL, // fill
				// GridBagConstraints.HORIZONTAL, // fill
				insets, // insets
				0, // ipadx
				0); // ipady
		this.add(c, constraints);
	}
	
	//-----------------------------------------------------------------------
    // Dynamischer Teil der GUI: die Event-Handler (hkw06) ------------------
    //-----------------------------------------------------------------------
  /**
   * ActionEvent: Button come 
   * Jemand meldet sich an der GUI an, indem er den kommen-Knopf 
   * drückt. Der ComeListener aktiviert die anderen Knöpfe und 
   * deaktiviert sich selbst. Die Uhrzeit der Anmeldung wird 
   * später protokolliert.
   */ 
  private class ComeListener implements ActionListener {
      public void actionPerformed(ActionEvent ae) {
        /*
         * Name und Kennwort überprüfen
         */
        String username = tfUser.getText();
        char[] pwdChars = tfPwd.getPassword();
        String pwd = "";
        for (int i = 0; i < pwdChars.length; i++) pwd += pwdChars[i];
        System.out.println("user=" + username + ", pwd=" + pwd);
        if (username.equals(pwd)) {
            System.out.println("ComeListener(): falsche user/password Eingabe");
            tfUser.selectAll();
            tfUser.requestFocus();
    		JOptionPane.showMessageDialog(null,
    				"Sie haben einen falschen Benutzername oder "
    				+ "ein falsches Kennwort eingegeben");
            return;
        }
        
        bLogin.setEnabled(false);
        bLogoff.setEnabled(true);
        bFire.setEnabled(true);
        cbBrennelement.setEnabled(true);
      }
  }
  
  /** 
   * ActionEvent: Button go 
   * Jemand meldet sich an der GUI ab, indem er den gehen-Knopf drückt.
   * Der GoListener aktiviert den kommen-Knopf und deaktiviert
   * sich selbst. Die Uhrzeit der Anmeldung wird später protokolliert.
   */
  private class GoListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      bLogin.setEnabled(true);
      bLogoff.setEnabled(false);
      bFire.setEnabled(false);
      cbBrennelement.setEnabled(false);
    }
  }
  
    //-----------------------------------------------------------------------
  /**
   * ActionEvent: Button burn 
   * Der BurnListener übergibt das gewählte Brennelement aus der GUI
   * an die load Methode als String zusammen mit der Uhrzeit.
   */
  private class BurnListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      String brennelement = (String)cbBrennelement.getSelectedItem();
      oc.verbrennen(brennelement);
     }
  }
  
    //-----------------------------------------------------------------------
  /**
   * TextEvent: bei Änderung in den zwei Textfeldern
   * Der DelimiterListener sorgt dafür, dass bei einem TextFeld
   * nur eine maximale Anzahl Zeichen genutzt wird.
   */
  private class DelimitorListener implements TextListener {
    private int limit;
    
    public DelimitorListener(int value) {
      this.limit = value;
    }
    
    public void textValueChanged(TextEvent te) {
      TextComponent tc = (TextComponent)te.getSource();
      String line = tc.getText();
      
      int pos = tc.getCaretPosition();
      if (line.length() > limit ) {
        tc.setText(line.substring(0, limit));
        tc.setCaretPosition(pos);
      }
      System.out.println(line);
    }
  }

 
}