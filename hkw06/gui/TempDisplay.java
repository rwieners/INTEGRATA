/*TempDisplay*/ 
package de.integrata.hkw06.gui;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import de.integrata.hkw06.ofen.*;

/**
 * Das TempDisplay zeigt die Temperatur des Ofens an. 
 * Das Display soll jedoch nicht via Polling beim Ofen nachschauen, ob
 * sich die Temperatur geändert hat, sondern der Ofen soll über das
 * Model View Controler Konzept mittels Observer Design-Pattern
 * das Display benachrichtigen.
 *
 * @version 1.3		4.5.03, krg: use Argument in update method
 */
public class TempDisplay extends Frame implements Observer {
	private static final long serialVersionUID = 1L;

  private Label lblTemp;
  private Ofen ofen;
  
  public TempDisplay(Ofen ofen) {
		this.ofen = (Ofen)ofen;
		System.out.println("TempDisplay(): Registrierung beim Model");
		
		// GUI stuff
		this.setLayout(new FlowLayout());
		lblTemp = new Label(" 000", Label.CENTER);
		lblTemp.setFont(new Font("Arial", Font.BOLD, 40));
		lblTemp.setForeground(Color.red);
		int i = 20;
		// Temp vom Ofen holen
		lblTemp.setText((new Integer(i).toString()));
		this.add(lblTemp);
		this.pack();
		this.toFront();
		this.setTitle("Temp-Display");
		this.setLocation(700, 300);
		//this.setLocationRelativeTo(null);
		this.setSize(220, 90);
		this.setResizable(false);
		this.setBackground(Color.decode("#0000dd"));
		this.setVisible(true);
	}

	/**
	 * Die Benachrichtigung einer Änderung des Models
	 */
	@Override
	public void update(Observable o, Object arg) {
		String text = arg.toString();
		System.out.println("text=" + text);
		lblTemp.setText(text);
	}



  	//Test
  public static void main(String[] args) {
  	new TempDisplay(null);
  }


}
