package kodierung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Klasse StreamKoordinierung
 * Diese Klasse kuemmert sich um die verschiedenen Modi der Anwendung der Caesar-Chiffre und ist fuer die den In-/Output von Dateien zustaendig
 * @author Lukas
 * @version 1.0
 */
public class StreamKoordinierung {
	private boolean chiffrieren;
	private byte schluessel;
	private String startText;
	private String endText;
	private String startDatei;
	private String endDatei;
	
	
	/**
	 * Standart-Konstruktor, legt wichtige Anfangswerte fest und initialisiert
	 * @param c bezeichnet den gewollten Modus. True - Chiffrieren , False - Dechiffrieren
	 * @param s bezeichnet den zu verwendenden Schluessel, Wert '88' falls ein ZufallsSchluessel verwendet werden soll
	 * @param startDatei Name der Datei mit dem Rohtext
	 * @param endDatei Name der Datei mit dem fertig bearbeiteten Text
	 */
	public StreamKoordinierung(boolean c, byte s, String startDatei, String endDatei) {
		this.chiffrieren = c;
		this.schluessel = s;
		this.startDatei = startDatei;
		this.endDatei = endDatei;
		this.startText = new String("");
		this.endText = new String("");
	}
	
	/**
	 * Unterscheidet alle drei verschiedenen Modi und erzeugt anhand dieser eine Chiffre-Instanz, die sie dem Klassenattribut zuweist
	 * Danach wird ueber die Funktionen der Chiffre-Instanz der vorliegende Text bearbeitet und das Ergebnis in das Klassenattribut endText geschrieben
	 */
	public void koordiniereAnhandDerEingabedaten() {

		String ausgabe = new String("");
	//	chiffre.beseitigeSonderzeichen();
		// Evaluiere zwischen den Modi
		if(chiffrieren) {
			Chiffrierung chiffrierung;
			// Operationen mit Schluessel
			if(schluessel == 88) {
				//Benutze zufaelligen Schluessel
				schluessel = (byte)(Math.random() * 26);
				chiffrierung = new Chiffrierung(this.startText, this.schluessel);
				chiffrierung.beseitigeSonderzeichen();
				System.out.println("Als Schluessel verwenden wir: "+this.schluessel);
			} else {
				chiffrierung = new Chiffrierung(this.startText, this.schluessel);
				chiffrierung.beseitigeSonderzeichen();
			}
			ausgabe = chiffrierung.chiffriere();
			System.out.println("Den verschluesselte Text finden sie nun in ihrer Ausgabedatei. Als Vorgeschmack:\n\n" + ausgabe);
		}
		else { // Hier Dechiffrieren
			Dechiffrierung dechiffrierung = new Dechiffrierung(this.startText, this.schluessel);
			
			if(this.schluessel == 88) {
				// Operationen Ohne Schluessel
				boolean modus = getDechiffrierMethode(); // True -> Zufall False -> Kryptanalyse
				if(modus) {
					ausgabe = dechiffrierung.dechiffriereOhneSchluessel();
				}
				else {
					ausgabe = dechiffrierung.kryptAnalyse();
				}
			}
			else {
				// Operationen mit Schluessel
				ausgabe = dechiffrierung.dechiffriereMitSchluessel();	
			}
			System.out.println("Den dechiffrierten Text finden sie in ihrer Ausgabedatei. Als Vorgeschmack:\n\n" + ausgabe);
		}
		this.endText = ausgabe;
	//	System.out.println(this.endText + " <-- EndTExt"); // Debug
	}
	
	/**
	 * Liest anhand der startDatei den darin befindlichen Text ein und weist diesen dem Klassenattribut startText zu.
	 * @return  Gibt true bei erfolgreichem Text-lesen aus, sonst false
	 */
	public boolean liesStarttextAusDatei() {
		boolean erfolg = true;
		StringBuilder lesen = new StringBuilder("");

			// Einlesen
		
			try (BufferedReader br = new BufferedReader(new FileReader(new File(this.startDatei)))) 	
			{
				String s = new String("");
				while((s = br.readLine())!= null) {
					lesen.append(s);
					lesen.append("\n");
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
		//System.out.println(this.startText);
		return erfolg;
	}
	
	/**
	 * Schreibt den vorhandenen endText in eine Datei mit dem Namen des in endDatei befindlichen Strings
	 */
	public void schreibeEndtextInDatei() {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(endDatei)));
			bw.write(endText);
			bw.close();
		}
		catch(IOException ioex) {
			System.out.println("Fehler beim Erstellen des Writers bzw. beim Schreiben, bitte mit anderem Datei-Namen versuchen!");
		}
		catch(Throwable t) {
			System.out.println("End-Datei konnte nicht erstellt werden. Bitter erneut versuchen!");
		}

	}
	
	private boolean getDechiffrierMethode() {
		boolean ergebnis = false;
		String eingabe = new String("");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean wiederhole;
		do {
		wiederhole= true;
		System.out.println("Waehlen Sie zum Schluss nun bitte die Methode anhand derer der Text entschluesselt werden soll.\nDabei geben sie bitte 'K' fuer eine Kryptanalyse ein oder 'Z' fuer eine zufallsbasierte Loesung!");
	
		try {
			eingabe = br.readLine().toLowerCase().trim();
		}
		catch(IOException ioex) {
			System.out.println("Konnte die Eingabe nicht lesen! Bitte versuchen sie es erneut.");
			eingabe = "";
			continue;
		}
		if((eingabe.equals("k") || eingabe.equals("z"))) {
			wiederhole = false;
		}
		else {
			System.out.println("Leider konnten wir ihre Eingabe nicht richtig deuten. Bitte achten sie auf die zulaessigen Zeichen!");
		}
			
	}
	while(wiederhole);
		
	if(eingabe.equals("k")) {
		ergebnis = false;
	}
	else {
		ergebnis = true;
	}
	
	return ergebnis;
	}
}
