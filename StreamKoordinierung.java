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
	private String startDatei;
	private String endDatei;
	private Chiffre chiffre;
	
	public StreamKoordinierung(boolean c, byte s, String startDatei, String endDatei) {
		this.chiffrieren = c;
		this.schluessel = s;
		this.startDatei = startDatei;
		this.endDatei = endDatei;
		this.startText = new String("");
		this.endText = new String("");
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
	//	System.out.println(this.endText + " <-- EndTExt"); // Debug
	}
	
	
	public boolean liesStarttextAusDatei() {
		boolean erfolg = true;
		StringBuilder lesen = new StringBuilder("");

			// Einlesen
		
			try (BufferedReader br = new BufferedReader(new FileReader(new File(this.startDatei)))) 	
			{
				String s = new String("");
				while((s = br.readLine())!= null) {
					lesen.append(s);
				}
			}
			catch(FileNotFoundException fnfex) {
				System.out.println("Konnte die Datei nicht finden! Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
				erfolg = false;
			}
			catch(NullPointerException npex) {
				System.out.println("Konnte nicht auf die Datei zugreifen! Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
				erfolg = false;
			}
			catch(IOException ioex) {
				System.out.println("IO-Fehler! Bitte Datei ueberpruefen!");
				erfolg = false;
			}
			catch(Throwable t) {
				System.out.println("Fehler beim Einlesen des Start-Textes. Bitte ueberpruefen sie den DateiNamen, gegebenenfalls den Pfad.");
				erfolg = false;
			}
		
		// StartText uebergeben
		this.startText = lesen.toString();
		
		return erfolg;
	}
	
	
	public void schreibeEndtextInDatei() {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(endDatei)));
			// bw.write(endText);
			bw.close();
		}
		catch(IOException ioex) {
			System.out.println("Fehler beim Erstellen des Writers bzw. beim Schreiben, bitte mit anderem Datei-Namen versuchen!");
		}
		catch(Throwable t) {
			System.out.println("End-Datei konnte nicht erstellt werden. Bitter erneut versuchen!");
		}

	}
}
