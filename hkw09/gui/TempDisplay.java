/*TempDisplay*/ 
package de.integrata.hkw09.gui;

import java.awt.*;
import java.util.*;

import de.integrata.hkw09.ofen.*;

/**
 * Das TempDisplay zeigt die Temperatur des Ofens an. 
 * Das Display soll jedoch nicht via Polling beim Ofen nachschauen, ob
 * sich die Temperatur geändert hat, sondern der Ofen soll über das
 * Model View Controler Konzept mittels Observer Design-Pattern
 * das Display benachrichtigen.
 *
 * @version 1.9.1115
 */
public class TempDisplay extends Frame implements java.util.Observer {
	private static final long serialVersionUID = 1L;

  private Label lblTemp, lblText;
  private Ofen ofen;
  
  public TempDisplay(Observable ofen) {
		this.ofen = (Ofen)ofen;
		System.out.println("TempDisplay(): Registrierung beim Model");
		ofen.addObserver((Observer)this);	//Eigenes Object beim Modell registrieren
		
		// GUI stuff
		this.setLayout(new FlowLayout());
		lblText = new Label("Ist-Temperatur: ", Label.CENTER);
		lblText.setFont(new Font("Arial", Font.BOLD, 30));
		lblText.setForeground(Color.red);
		this.add(lblText);
		
		lblTemp = new Label(" 000", Label.CENTER);
		lblTemp.setFont(new Font("Arial", Font.BOLD, 30));
		lblTemp.setForeground(Color.red);
		int i = ((Ofen)ofen).getIstTemperatur();
		lblTemp.setText((new Integer(i).toString()));
		this.add(lblTemp);
		this.pack();
		this.toFront();
		this.setTitle("Temperatur-Anzeige digital");
		this.setLocation(700, 300);
		//this.setLocationRelativeTo(null);
		//this.setSize(300, 100);
		this.setResizable(false);
		//this.setBackground(Color.decode("#0000dd"));
		this.setVisible(true);
	}

	/**
	 * Die Benachrichtigung einer Änderung des Models
	 */
  public void update (Observable o, Object arg) {
		if (arg == null) {
			// Die neuen Daten vom Ofen holen und anzeigen
			lblTemp.setText(" " + ((Ofen)ofen).getIstTemperatur());
		}
		else {	//IstTemperatur wird als Argument geliefert
			lblTemp.setText(" " + ((Integer)arg).toString());
		}
	}

}
