/*	Program name:	Lab 10 Vessel Dispatch Utility
	Programmer:		Marcus Ross
	Date Due:		22 Nov 2013
	Description:	This program asks the user for information on a ship in distress, asks for the name of a file where information on Coast Guard ships is held, then shows a table that helps in dispatching Coast Guard ships.	*/

package lab10;

import stuff.MyClass;
import lab10.CGVessel;
import lab10.MaydayVessel;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		MaydayVessel maydayVessel = new MaydayVessel();
		CGVessel[] cgVessel = new CGVessel[7]; // ugh--hard code--but I don't want to have to change the sequence diagram... ironic how the type of design that suits objects actually restricts my freedom!
		BufferedReader cgFile;

		LoadMayday(maydayVessel);
		while(true) {
			cgFile = GetCGFile();
			try {
				for(int i = 0; i < cgVessel.length; i++) {
					cgVessel[i] = new CGVessel(cgFile);
				}
				break;
			} catch(IOException e) {
				MyClass.errorDialog("File I/O failed.");
			} catch(NumberFormatException e) {
				MyClass.errorDialog("Invalid file format.");
			}
		}
		ShowTable(cgVessel, maydayVessel);
	}

	public static void LoadMayday(MaydayVessel maydayVessel) {
		double x, y;
		String n;
		int p;
		Date t;

		JTextField xPos = new JTextField();
		JTextField yPos = new JTextField();
		JTextField name = new JTextField();
		JTextField personnel = new JTextField();
		Object[] message = {"Vessel in Distress", "X position", xPos, "Y position", yPos, "Name", name, "Personnel on board", personnel}; // components of input dialog

		while(true) {
			JOptionPane.showMessageDialog(null, message, "Vessel Dispatch Utility", JOptionPane.QUESTION_MESSAGE);
			try { // create new MaydayVessel via constructor using all four fields of the dialog
				if(!name.getText().isEmpty()) {
					x = Double.parseDouble(xPos.getText());
					y = Double.parseDouble(yPos.getText());
					n = name.getText();
					p = Integer.parseInt(personnel.getText());	
					t = new Date(); // hope this is acceptable in lieu of asking the user
					maydayVessel.load(x, y, n, p, t);
					return;
				} else
					MyClass.errorDialog("Please fill all fields.");
			} catch(NumberFormatException e) {
				MyClass.errorDialog("Invalid entry.");
			}
		}
	}

	public static BufferedReader GetCGFile() {
		BufferedReader cgFile;
		String cgFileN;
		JTextField field = new JTextField();
		Object[] message = {"File with Coast Guard vessel data", field};

		while(true) {
			JOptionPane.showMessageDialog(null, message, "Vessel Dispatch Utility", JOptionPane.QUESTION_MESSAGE);
			cgFileN = field.getText();
			if(cgFileN == null) {
				MyClass.errorDialog("Please enter a file name.");
				continue;
			} else {
				if(cgFileN.isEmpty()) {
					MyClass.errorDialog("Invalid entry.");
					continue;
				}
			}

			try {
				cgFile = new BufferedReader(new FileReader(cgFileN));
			} catch(FileNotFoundException e) {
				MyClass.errorDialog("File not found.");
				continue;
			}

			return cgFile;
		}
	}

	public static void ShowTable(CGVessel[] cgVessel, MaydayVessel maydayVessel) {
		DateFormat dfm = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
		JTextArea message = new JTextArea();
		message.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		message.setEditable(false);

		message.append(String.format("%-12s%s\n", "NAME", maydayVessel.getName()));
		message.append(String.format("%-12s%5.2f\n", "X POSITION", maydayVessel.getXPos()));
		message.append(String.format("%-12s%5.2f\n", "Y POSITION", maydayVessel.getYPos()));
		message.append(String.format("%-12s%d\n", "PERSONNEL", maydayVessel.getPersonnel()));
		message.append(String.format("%-12s%s\n", "TIME", dfm.format(maydayVessel.getTime())));
		message.append(String.format("\n%7s%7s%7s%7s\n", "VESSEL", "CPCTY", "DIST", "ETA"));

		for(int i = 0; i < cgVessel.length; i++) {
			message.append(String.format("%7d%7d%7.2f%7.2f\n", i + 1, cgVessel[i].getCap(), cgVessel[i].getDist(maydayVessel), cgVessel[i].getETA(maydayVessel)));
		}

		Object[] noButtons = { }; // wouldn't want a dispatcher accidentally closing the window prematurely
		JOptionPane.showOptionDialog(null, message, "Vessel Dispatch Utility", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, noButtons, null);
	}
}