package kodierung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Klasse Verwaltung
 * Diese Klasse kuemmert sich um den Programmablauf und die Wuensche des Nutzers. Dabei liest sie den Modus, den Schluessel und die
 * gewuenschten Namen fuer Start-und EndDatei ein. Danach erzeugt sie eine textuelle Ausgabe der Chiffrierung
 * @author Lukas
 * @version 1.0
 */
public class Verwaltung {

	private StreamKoordinierung streamKoordinierung;
	
	/**	 
	 * Standart-Konstruktor 
	 */
	public Verwaltung() {
		
	}
	
	/**
	 * Steuert den Programmablauf und ist Anlaufpunkt fuer die Main-Klasse
	 */
	public void steuern() {
		einlesenUndInitialisieren();
		ausgabe();
	}
	
	/**
	 * Gibt den Monolog zum Nutzer hin aus und erwartet seine Eingaben. Diese werden auf die privaten Methoden zur besseren Uebersichtlichkeit
	 * weitergeleitet. Dann werden die eingelesenen Werte zur Erzeugung einer Streamkoordinierung-Instanz benutzt, die als Klassenattribut gespeichert wird. 
	 */
	// Soll alle benoetigten Werte einlesen
	public void einlesenUndInitialisieren() {
		
		// Willkommenstext
		System.out.println("Willkommen zur Caesar-Kodierung!\nMoechten Sie einen Text chiffrieren oder dechiffrieren?");
		boolean chiffrieren = getModus();
		
		// Schluessel-Eingabe
		if(chiffrieren) {
			System.out.println("Bitte geben sie jetzt den Schluessel ein, der zum Chiffrieren verwendet werden soll. Entscheiden Sie sich\ndabei fuer eine Ganzzahl zwischen 1 und 25.\nMoechten sie einen zufaelligen Schluessel, geben sie bitte '88' ein.");
		}
		else {
			System.out.println("Wenn sie den fuer die Dechiffrierung benoetigten Schluessel kennen, geben sie ihn jetzt bitte ein. Sollten\nSie ihn nicht kennen, geben sie bitte die '88' ein.");
		}
		byte schluessel = getSchluessel();
		
		// Als naechstes die Datei-Namen
		boolean erfolgreichEingelesen = false;
		do {
		
			String[] namen = getDateiNamen();
			String eingabeDatei = namen[0];
			String ausgabeDatei = namen[1];
		
			// Nun haben wir alles und leiten die Initialisierung ein
		
			this.streamKoordinierung = new StreamKoordinierung(chiffrieren, schluessel, eingabeDatei, ausgabeDatei );
			erfolgreichEingelesen = this.streamKoordinierung.liesStarttextAusDatei();
			if(!erfolgreichEingelesen) {
				System.out.println("\n\nBeim Lesen der Datei trat ein Fehler auf, bitte geben sie erneut die DateiNamen an!\n");
			}
		}
		while(!erfolgreichEingelesen);
		
		System.out.println("\nDie Eingabe lief erfolgreich ab, wir beginnen nun mit der De-/Chiffrierung.");
	}
	
	/**
	 * Diese Funktion koordiniert die Instanz der Streamkoordinierung und sorgt fuer die Ausgabe des fertigen Textes in Datei-Form.
	 */
	// Schlussendliche Textausgabe auf der Konsole
	public void ausgabe() {
		this.streamKoordinierung.koordiniereAnhandDerEingabedaten();
		this.streamKoordinierung.schreibeEndtextInDatei();
		System.out.println("\n\nAlles erfolgreich abgelaufen, herzlichen Glueckwunsch!");
	}
	
	//*****************************
	// Der Uebersichtlichkeit wegen
	//*****************************
	
	/**
	 * Fragt den Modus in einer While-Schleife bis zur erfolgreichen Abfrage ab, um einmalige Ausfuehrung des Programms zu gewaehrleisten
	 * @return True falls Modus -> Chiffrieren, False falls Modus -> Dechiffrieren
	 */
	private boolean getModus() {
		boolean chiffrieren;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String eingabe = new String("");
		// Modus einlesen
				boolean wiederhole1;
				do {
				wiederhole1 = true;
				System.out.println("Bitte geben sie für Chiffrieren 'c' ein, für Dechiffrieren 'd'!");
			
				try {
					eingabe = br.readLine().toLowerCase().trim();
				}
				catch(IOException ioex) {
					System.out.println("Konnte die Eingabe nicht lesen! Bitte versuchen sie es erneut.");
					eingabe = "";
					continue;
				}
				if((eingabe.equals("c") || eingabe.equals("d"))) {
					wiederhole1 = false;
				}
				else {
					System.out.println("Leider konnten wir ihre Eingabe nicht richtig deuten. Bitte achten sie auf die zulaessigen Zeichen!");
				}
					
			}
			while(wiederhole1);
			
			// Modus-Unterscheidung
			if(eingabe.equals("c")) {
				chiffrieren = true;
				System.out.println("Sie wollen also einen Text mittels Caesar-Chiffre chiffrieren.");
			}
			else {
				chiffrieren = false;
				System.out.println("Sie wollen also einen Text mittels Caesar-Chiffre dechiffrieren.");
			}
		return chiffrieren;
	}
	
	/**
	 * Fragt den Schluessel in einer While-Schleife bis zur erfolgreichen Abfrage ab, um einmalige Ausfuehrung des Programms zu gewaehrleisten
	 * @return Den Schluessel als Byte im Intervall [0,25], falls zufaelliger Schluessel gewollt ist, Schluessel = 88
	 */
	private byte getSchluessel() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String eingabe = new String("");
		byte schluessel = 0;
		boolean wiederhole2;
		do {
			wiederhole2 = true;
			
			try {
				eingabe = br.readLine().toLowerCase().trim();
			}
		
			catch(IOException ioex) {
				System.out.println("Konnte die Eingabe nicht lesen! Bitte versuchen sie es erneut und verwenden Sie ausschliesslich Zahlen!");
				eingabe = "";
				continue;
			}
			try {
				schluessel = Byte.parseByte(eingabe);
				
			}	
			catch (NumberFormatException nfex) {
				System.out.println("Konnte die Eingabe nicht zu einer Zahl konvertieren! Bitte erneut versuchen!");
				schluessel = 30;
			
			}
			// Formatierung des Schluessels
			if((schluessel >=26 || schluessel <0 )&& (schluessel != 88)) {
				System.out.println("Der Wert des Schluessels muss im Intervall [0,25] liegen, bzw. 88 sein! Bitte nochmals eingeben!");
			
			}
			else if(schluessel == 0) {
				System.out.println("Der Schluessel-Wert 0 veraendert bei der Caesar-Kodierung nichts. Bitte gib einen anderen Schluessel ein!");
			}
			else {
			wiederhole2 = false;
			}
		}
		while(wiederhole2);
	
		// Schluesseleingabe ist erfolgt, 88 steht fuer Unbekannter Wert
		return schluessel;
	}
	/**
	 * Fragt die gewollten Datei-Namen in einer While-Schleife bis zur erfolgreichen Abfrage ab, um einmalige Ausfuehrung des Programms zu gewaehrleisten
	 * @return String-Array mit eingabeDatei an 0. Stelle und ausgabeDatei an 1. Stelle
 	 */
	
	private String[] getDateiNamen() {
		String[] dateiNamen = new String[2];
		

		// Nun also Dateinamen fuer Ein- und Ausgabedatei
		String eingabe = new String("");
		String eingabeDatei = new String("");
		String ausgabeDatei = new String("");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean wiederhole3;
		
		do {
			wiederhole3 = true;
			eingabe = "";	
			System.out.println("Weiter geht's mit den Dateinamen. Bitte geben sie innerhalb einer Eingabe zuerst den der Eingabedatei,\ndanach den der Ausgabedatei an und trennen sie die Beiden mittels '%'!\nFaellt Ihnen kein Name ein, werden Standart-Namen (Input := startDatei.txt; Output := endDatei.txt) verwendet.");
			
			try {
				eingabe = br.readLine().trim();
				if(eingabe.equals("")) {
					eingabe = "startDatei.txt%endDatei.txt";
				}
				else if(!eingabe.contains("%")) {
					System.out.println("Da in ihrer Eingabe kein Trenn-Operator vorhanden war, benutzen wir die Eingabe als Start-Dateinamen.");
					eingabe = new String(eingabe + "%");
				}
			}
			
			catch(IOException ioex) {
				System.out.println("Konnte die Eingabe nicht lesen! Bitte versuchen sie es erneut!");
				eingabe = "startDatei.txt%endDatei.txt";
				continue;
			}
			System.out.println(eingabeDatei);
			System.out.println(ausgabeDatei);
			// Fallunterscheidung der Dateinamen
			
			
			String[] getrennteNamen = eingabe.split("%");
			if(getrennteNamen.length == 1) {
				eingabeDatei = eingabe.split("%")[0];
				ausgabeDatei = "endDatei.txt";
			}
			else if(getrennteNamen.length == 2) {
				eingabeDatei = eingabe.split("%")[0];
				if(eingabeDatei.equals("")) {
					eingabeDatei = "startDatei";
				}
				ausgabeDatei = eingabe.split("%")[1];
			}
			else if(getrennteNamen.length > 2) {
				System.out.println("Mehr als zwei Namen sind nicht zulaessig, es werden Standard-Namen verwendet.");
				eingabeDatei = "startDatei.txt";
				ausgabeDatei = "endDatei.txt";
			}
			else {
				System.out.println("Es wird versucht auf die Datei 'startDatei.txt' zuzugreifen.");
				eingabeDatei = "startDatei.txt";
				ausgabeDatei = "endDatei.txt";
			}
				
			wiederhole3 = false;
		}
		while(wiederhole3);
		
		dateiNamen[0] = eingabeDatei;
		dateiNamen[1] = ausgabeDatei;
		
		return dateiNamen;
	}
}
