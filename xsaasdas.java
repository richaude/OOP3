package wuerfeltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class xsaasdas {
	
	
	public static String eingabe() {
		
		boolean abbruch;
		do {
			abbruch = false;
			System.out.println("Gib bitte  den Klartext-Dateinamen ein!");
			
			
			try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
				String s = br.readLine();
			}
			catch(IOException ioex) {
				System.out.println("Konnte die eingegebene Zeile nicht lesen!");
				abbruch = true;
			}
			
		}
		while(abbruch == true);
		
		System.out.println("ende erreicht");
		return null;
	
	}
	

}
