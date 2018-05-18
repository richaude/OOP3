package kodierung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Diese Klasse ist fuer die verschiedenen Entschluesselungsarten verantwortlich.
 * @author richard
 *
 */
public class Dechiffrierung extends Chiffre {
	
	private String text;
	private final String haeufigkeitstabelle = "enisratdhulcgmobwfkzpvjyxq";
	private byte schluessel;
	
	/**
	 * Konstruktor
	 * @param text Der Text, der entschluesselt werden soll.
	 * @param schluessel Der Schluessel, der beim Entschluesseln evtl. gebraucht wird.
	 */
	public Dechiffrierung(String text, byte schluessel) {
		super(text, schluessel);
		this.text=text;
		this.schluessel=schluessel;
	}
	
	/**
	 * Diese Methode dechiffriert den Text im Konstruktor und nutzt dafuer den ebenfalls im Konstruktor angegebenen Wert des Schluessels.
	 * @return Den entschluesselten String.
	 */
	public String dechiffriereMitSchluessel() {
		StringBuilder entschluesselt = new StringBuilder();
		for (int i=0; i<text.length(); i++) {
			if (((int)text.charAt(i)>96) && ((int)text.charAt(i)<123)) {
				entschluesselt.append(verschiebeCharacter(text.charAt(i), false, schluessel));
			} else {
				entschluesselt.append(text.charAt(i));
			}
		}
		return entschluesselt.toString();
	}
	
	/**
	 * Diese Methode dechiffriert den Text im Konstruktor, indem fuer den Schluessel alle Werte ab 1 durchprobiert werden.
	 * @return Den entschluesselten Text, der vorher durch die Interaktion des Nutzers bestaetigt wurde.
	 */
	public String dechiffriereOhneSchluessel() {
		StringBuilder entschluesselt = new StringBuilder();
		System.out.println("Dechiffrierung ohne Schluessel: Es werden alle Schluessel von 1 aufwaerts durchgegangen!\n");
		boolean weitermachen = true;
		byte aktuellerSchluesselwert = 1;
		do {
			StringBuilder aktuellerStringbuilder = new StringBuilder();
			System.out.println("Wert, mit dem gerade entschluesselt wird: "+aktuellerSchluesselwert+"\n");
			schluessel = aktuellerSchluesselwert;
			for (int i=0; i<text.length(); i++) {
				if (((int)text.charAt(i)>96) && ((int)text.charAt(i)<123)) {
					aktuellerStringbuilder.append(verschiebeCharacter(text.charAt(i), false, aktuellerSchluesselwert));
				} else {
					aktuellerStringbuilder.append(text.charAt(i));
				}
			}
			System.out.println("Ist dies ein Treffer?\n"+aktuellerStringbuilder+"\nDruecken Sie 'j' fuer Ja, 'n' fuer Nein.");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String antwort = new String("");
			try {
				antwort = br.readLine().toLowerCase();
				if (antwort.equals("j")) {
					weitermachen = false;
					entschluesselt.append("Korrekter Schluessel: "+aktuellerSchluesselwert+"\n");
					entschluesselt.append("Entschluesselter Text:\n"+aktuellerStringbuilder);
				} else if (antwort.equals("n")) {
					aktuellerSchluesselwert++;
					continue;
				} else {
					System.out.println("Die Eingabe entsprach nicht den angegebenen Parametern. Bitte nochmal versuchen!");
					continue;
				}
				
			} catch (IOException e) {
				System.out.println("Etwas beim Einlesen ging schief.");
			} catch (Throwable t) {
				System.out.println("Oops, something went wrong.");
			}
		} while (weitermachen);
		return entschluesselt.toString();
	}
	
	/**
	 * Diese Methode implementiert die Kryptanalyse, wo die Differenz zwischen dem haeufigsten Buchstaben im Geheimtext und dem haeufigsten Buchstaben der deutschen Sprache verwendet wird. Ist die Entschluesselung nicht erfolgreich, wird durch Eingabe des Nutzers zum naechsthaeufigen Buchstaben der deutschen Sprache fortgeschritten.
	 * @return Den entschluesselten Text, dessen Korrektheit durch Eingabe des Nutzers bestaetigt wurde.
	 */
	public String kryptAnalyse() {
		StringBuilder entschluesselt = new StringBuilder();
		System.out.println("Kryptanalyse: Entschluesselt wird basierend auf der Differenz zwischen dem haeufigsten Buchstaben im Text und dem haeufigsten Buchstaben der deutschen Sprache.\n");
		boolean weitermachen = true;
		int aktuelleStelle = 0;
		char haeufigsterChar = findeHaeufigstes().get(0);
		//System.out.println(haeufigsterChar);
		int asciiWertHaeufigsterChar = (int)haeufigsterChar;
		do {
			StringBuilder aktuellerStringbuilder = new StringBuilder();
			System.out.println("Buchstabe in der Haeufigkeitstabelle fuer die Berechnung der Differenz: "+haeufigkeitstabelle.charAt(aktuelleStelle)+"\n");
			int asciiWertAktuellerChar = (int)haeufigkeitstabelle.charAt(aktuelleStelle);
			byte differenz = (byte) (asciiWertHaeufigsterChar-asciiWertAktuellerChar);
			if (differenz<0) {
				differenz = (byte) (26+differenz);
			}
			//System.out.println(differenz);
			for (int i=0; i<text.length(); i++) {
				if (((int)text.charAt(i)>96) && ((int)text.charAt(i)<123)) {
					aktuellerStringbuilder.append(verschiebeCharacter(text.charAt(i), false, differenz));
				} else {
					aktuellerStringbuilder.append(text.charAt(i));
				}
			}
			System.out.println("Ist dies ein Treffer?\n"+aktuellerStringbuilder+"\nDruecken Sie 'j' fuer Ja, 'n' fuer Nein.\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String antwort = new String("");
			try {
				antwort = br.readLine().toLowerCase();
				if (antwort.equals("j")) {
					weitermachen = false;
					entschluesselt.append("Aktueller Buchstabe, auf dessen Wertigkeit die Differenz basiert: "+haeufigkeitstabelle.charAt(aktuelleStelle));
					entschluesselt.append("\nEntschluesselter Text:\n"+aktuellerStringbuilder);
				} else if (antwort.equals("n")) {
					aktuelleStelle++;
					continue;
				} else {
					System.out.println("Die Eingabe entsprach nicht den angegebenen Parametern. Bitte nochmal versuchen!");
					continue;
				}
			} catch (IOException e) {
				System.out.println("Beim Einlesen ging etwas schief.");
			} catch (Throwable t) {
				System.out.println("Irgendwas ging schief");
			}
		} while (weitermachen);
		return entschluesselt.toString();
	}
	
	/**
	 * Diese Methode ist eine Hilfsmethode zum Finden des haeufigsten Buchstaben im Text.
	 * @return Eine Liste von Charactern, absteigend nach Haeufigkeit geordnet.
	 */
	private List<Character> findeHaeufigstes() {
		Map<Character, Integer> map = new HashMap<>();
        List<Character> einzigartigeChars = new ArrayList<>();
        //AlphabetSet zum Enthaltensein des Characters befuellen
        Set<Character> buchstabenMenge = new HashSet<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i=0; i<alphabet.length(); i++) {
        	buchstabenMenge.add(alphabet.charAt(i));
        }
        //Hashmap erstellen, Schluessel ist der Char in dem Text, Wert ist, wie oft er vorkommt
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            //String buchstabenMenge;
			if (!einzigartigeChars.contains(ch) && buchstabenMenge.contains(ch)) {
            	einzigartigeChars.add(ch);
            }
            if (!buchstabenMenge.contains(ch))    // nicht-Buchstaben werden uebersprungen
                continue;

            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }
        //Liste mit den Haeufigkeitswerten wird befuellt
        List<Integer> wieOftDerChar = new ArrayList<>();
        for (int i=0; i<einzigartigeChars.size(); i++) {
        	wieOftDerChar.add(map.get(einzigartigeChars.get(i)));
        }
        //der Groesse nach absteigend sortiert
        Collections.sort(wieOftDerChar, Collections.reverseOrder());
        List<Character> fertigsortiert = new ArrayList<>();
        //in doppelter For-Schleife wird an jeder Stelle des Integer-Array geschaut, ob der Value der Map an diesem Element uebereinstimmt
        //da von der nullten Stelle an geschaut wird, ist das Character-Array ebenfalls mit der Haeufigkeit absteigend sortiert
        for (int i=0; i<wieOftDerChar.size(); i++) {
        	for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            	if (entry.getValue() == wieOftDerChar.get(i)) {
            		if (!fertigsortiert.contains(entry.getKey())) {
                		fertigsortiert.add(entry.getKey());
            			}
            		}
            	}
        	}
        
        return fertigsortiert;
	}

}
