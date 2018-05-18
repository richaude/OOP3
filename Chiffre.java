package kodierung;

/**
 * Diese Klasse ist die Superklasse von Chiffrierung und Dechiffrierung
 * @author richard
 *
 */

public class Chiffre {
	
	private String text;
	private byte schluessel;
	
	/**
	 * Konstruktor
	 * @param text Der Text, der chiffriert werden soll
	 * @param schluessel Zahl der Stellen, um die verschoben werden soll
	 */
	public Chiffre(String text, byte schluessel) {
		this.text = text;
		this.schluessel = schluessel;
	}
	
	/**
	 * Verschiebt den spezifizierten Char in der Ascii-Folge um den Betrag des Werts modulo 26.
	 * @param current Der Char, der verschoben werden soll
	 * @param chiffrieren Ist 'true' fuer Chiffrieren, 'false' fuer das Dechiffrieren
	 * @param wert Der Wert, um den der Char im Alphabet verschoben werden soll
	 * @return Den verschobenen Char
	 */
	public char verschiebeCharacter(char current, boolean chiffrieren, byte wert) {
		wert = (byte) Math.abs(wert);
		int asciiWert = (int)current;
		int verschoben;
		if (chiffrieren) {
			verschoben = asciiWert+(((int)wert%26));
		} else {
			verschoben = asciiWert-(((int)wert%26));
		}
		char chiffrierterChar;
		if (verschoben>122) {
			int differenz = verschoben-122;
			int neuerAsciiWert = 96+differenz;
			chiffrierterChar = (char)neuerAsciiWert;
		} else if (verschoben<97) {
			int differenz = 97-verschoben;
			int neuerAsciiWert = 123-differenz;
			chiffrierterChar = (char)neuerAsciiWert;
		} else {
			chiffrierterChar = (char)verschoben;
		}
		return chiffrierterChar;
	}

}
