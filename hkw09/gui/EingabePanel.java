/*
 * Created on 28.07.2004
 * see also: core Java Technical Tipps July/2004 
 */
package de.integrata.hkw09.gui;

import java.awt.*;
import javax.swing.*;

import de.integrata.hkw09.ofen.*;

public class EingabePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton bLogin, bLogoff, bFire;

	private JTextField tfUser;

	private JPasswordField tfPwd;

	private JComboBox<String> cbBrennelement;

	private OfenControllerImpl oc;

	/** Shared insets for all Components */
	private static final Insets insets = new Insets(5, 20, 5, 20);

	public static void main(String args[]) {
		JFrame frame = new JFrame("GridBagLayout Demo 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = frame.getContentPane();

		JPanel ep = new EingabePanel(new ConfigurableOfenController());
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
	public EingabePanel(OfenControllerImpl oc) {
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
		for (int i = 0; i < feuerliste.length; i++) {
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

}