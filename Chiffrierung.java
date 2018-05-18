package kodierung;

import java.text.Normalizer;

/**
 * Die Chiffrierungsklasse ist fuer das saubere Verschluesseln verantwortlich.
 * @author richard
 *
 */

public class Chiffrierung extends Chiffre {
	
	private String text;
	private byte schluessel;
	
	/**
	 * Konstruktor
	 * @param text Der zu verschluesselnde Text.
	 * @param schluessel Der Betrag um den verschluesselt werden soll.
	 */
	public Chiffrierung(String text, byte schluessel) {
		super(text, schluessel);
		this.text=text;
		this.schluessel=schluessel;
	}
	
	/**
	 * Realisiert uebliche Umschreibung der Umlaute und des sz, beseitigt ausserdem alle diakritischen Zeichen
	 */
	public void beseitigeSonderzeichen() {
		text = text.toLowerCase();
		text = text.replaceAll("ß", "ss");
		text = text.replaceAll("ä", "ae");
		text = text.replaceAll("ö", "oe");
		text = text.replaceAll("ü", "ue");
		//regex zum Behandeln von diakritischen Zeichen
		text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		//System.out.println(text);
	}
	
	/**
	 * Verschluesselt den Text und verwendet dazu den Schluessel
	 * @return Den verschluesselten Text
	 */
	public String chiffriere() {
		StringBuilder verschluesselt = new StringBuilder();
		for (int i=0; i<text.length(); i++) {
			if (((int)text.charAt(i)>96) && ((int)text.charAt(i)<123)) {
				verschluesselt.append(verschiebeCharacter(text.charAt(i), true, schluessel));
			} else {
				verschluesselt.append(text.charAt(i));
			}
		}
		return verschluesselt.toString();
	}

}
