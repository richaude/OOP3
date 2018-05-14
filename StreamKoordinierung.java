package kodierung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StreamKoordinierung {
	private boolean chiffrieren;
	private byte schluessel;
	private String startText;
	private String endText;
	private Chiffre chiffre;
	
	public StreamKoordinierung(boolean c, byte s, String startText, String endText) {
		this.chiffrieren = c;
		this.schluessel = s;
		liesStarttextAusDatei();
		this.endText = endText;	//Fehlt StartText als String
	}
	
	
	public void koordiniereAnhandDerEingabedaten() {

		String ausgabe = new String("");
	//	chiffre.beseitigeSonderzeichen();
		
		// Evaluiere zwischen den Modi
		if(chiffrieren) {
			// Operationen mit Schluessel
			if(schluessel == 88) {
				//Benutze zufaelligen Schluessel
				schluessel = (byte)(Math.random() * 26);
				this.chiffre = new Chiffre(this.startText, this.schluessel);
			} else {
				this.chiffre = new Chiffre(this.startText, this.schluessel);
			}
			chiffre.beseitigeSonderzeichen();
			ausgabe = this.chiffre.chiffriere();
		}
		else {
			this.chiffre = new Chiffre(this.startText, this.schluessel);
			chiffre.beseitigeSonderzeichen();
			
			if(this.schluessel == 88) {
				// Operationen Ohne Schluessel
				ausgabe = chiffre.dechiffriereOhneSchluessel();	
			}
			else {
				// Operationen mit Schluessel
				ausgabe = chiffre.dechiffriereMitSchluessel();	
			}
		}
		this.endText = ausgabe;
		System.out.println(chiffre.kryptAnalyse());
	}
	
	
	public void liesStarttextAusDatei() {
		StringBuilder lesen = new StringBuilder("");
		BufferedReader bis;
		// Einlesen
		
		int a = 0; 
		try {
			bis = new BufferedReader(new FileReader(new File(startText)));

			do {
				a = bis.read();
				if(a != -1) {
					char c = (char) a;
					lesen.append(c);
				}
			}
			while(a != -1);
		}
		catch(FileNotFoundException fnfex) {
			System.out.println("Konnte die Datei nicht finden! Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
		}
		catch(NullPointerException npex) {
			System.out.println("Konnte die Datei nicht finden! Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
		}
		catch(IOException ioex) {
		System.out.println("IO-Fehler! Bitte Datei ueberpruefen!");
		}
		catch(Throwable t) {
			System.out.println("Fehler beim Einlesen des Start-Textes. Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
		}
		
		// StartText uebergeben
		this.startText = lesen.toString();
	}
	
	
	public void schreibeEndtextInDatei() {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(endText)));
			bw.write(endText);
		}
		catch(IOException ioex) {
			System.out.println("Fehler beim Erstellen des Writers bzw. beim Schreiben, bitte mit anderem Datei-Namen versuchen!");
		}
		catch(Throwable t) {
			System.out.println("End-Datei konnte nicht erstellt werden. Bitter erneut versuchen!");
		}

	}
}
