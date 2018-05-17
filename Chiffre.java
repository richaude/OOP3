package kodierung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Klasse Chiffre
 * Diese Klasse implementiert die verschiedenen Chiffrierungsmethoden
 * @author richard
 *
 */

public class Chiffre {
	
	private String text;
	private byte schluessel;
	private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private HashMap <Character, Integer> charToInt = new HashMap<>();
	private HashMap <Integer, Character> intToChar = new HashMap<>();
	private Set<Character> buchstabenMenge = new HashSet<>();
	
	/**
	 * Konstruktor, befuellt ausserdem die zwei fuer die Chiffrierung notwendigen HashMaps und ein Set mit dem Alphabet, das zum Gegenpruefen verwendet wird
	 * @param text Der Text, der chiffriert werden soll
	 * @param schluessel Zahl der Stellen, um die verschoben werden soll
	 */
	public Chiffre(String text, byte schluessel) {
		this.text = text;
		this.schluessel = schluessel;
		
		for (int i=0; i<alphabet.length(); i++) {
			intToChar.put(new Integer(i), alphabet.charAt(i));
			charToInt.put(alphabet.charAt(i), new Integer(i));
			buchstabenMenge.add(alphabet.charAt(i));
		}
	}
	
	/**
	 * beseitigt alle Sonderzeichen, macht z.B. aus ä ae
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
	
	public String chiffriere() {
		StringBuilder verschluesselt = new StringBuilder();
		
		for (int i=0; i<text.length(); i++) {
			if (buchstabenMenge.contains(text.charAt(i))) {
				int x = charToInt.get(text.charAt(i)).intValue();
				verschluesselt.append(intToChar.get(new Integer(x+(int)schluessel)%26));
			} else {
				verschluesselt.append(text.charAt(i));
			}
		}
		return verschluesselt.toString();
	}
	
	public String dechiffriereMitSchluessel() {
		StringBuilder entschluesselt = new StringBuilder();
		for (int i=0; i<text.length(); i++) {
			if (buchstabenMenge.contains(text.charAt(i))) {
				int x = charToInt.get(text.charAt(i)).intValue();
				int temp = (26-(int)schluessel);
				entschluesselt.append(intToChar.get(new Integer(x+temp)%26));
			} else {
				entschluesselt.append(text.charAt(i));
			}
		}
		return entschluesselt.toString();
	}
	
	public String dechiffriereOhneSchluessel() {
		StringBuilder entschluesselt = new StringBuilder();
		entschluesselt.append("Dechiffrierung ohne Schluessel:\nAnbei sind 25 Varianten, von denen nur eine die korrekte Variante ist!\n\n");
		
		for (int j=1; j<alphabet.length(); j++) {
			entschluesselt.append("Schluessel = "+j+":\n");
			for (int i=0; i<text.length(); i++) {
				if (buchstabenMenge.contains(text.charAt(i))) {
					int x = charToInt.get(text.charAt(i)).intValue();
					int temp = (26-j);
					entschluesselt.append(intToChar.get(new Integer(x+temp)%26));
				} else {
					entschluesselt.append(text.charAt(i));
				}
				
			}
			entschluesselt.append("\n\n");
		}
		return entschluesselt.toString();
	}
	
	public String kryptAnalyse() {
		String kryptDeutsch = "enisratdhulcgmobwfkzpvjyxq";
		List<Character> haeufigsteChars = new ArrayList<>();
		haeufigsteChars = findeHaeufigstes();
		char haeufigstes = haeufigsteChars.get(0);
		StringBuilder encrypt = new StringBuilder();
		System.out.println("\nKryptanalyse: Folgend sehen Sie verschiedene moegliche Entschluesselungsvarianten, die auf der Differenz des haeufigsten Buchstaben im Alphabet und des haeufigsten Buchstaben im Chiffre basieren:\n");
		int j = 0;
			boolean weitermachen = true;
			do {
				StringBuilder aktuellerStringBuilder = new StringBuilder();
				if (weitermachen) {
					System.out.println("\nBuchstabe, mit dem gerade entschluesselt wird: "+kryptDeutsch.charAt(j)+"\n");
					int differenz = charToInt.get(kryptDeutsch.charAt(j))-charToInt.get(haeufigstes);
					for (int i=0; i<text.length(); i++) {
						if (buchstabenMenge.contains(text.charAt(i))) {
							int aktuellerBuchstWert = charToInt.get(text.charAt(i));
							int temp = 26-differenz;
							aktuellerStringBuilder.append(intToChar.get(new Integer(aktuellerBuchstWert+temp)%26));
						} else {
							aktuellerStringBuilder.append(text.charAt(i));
						}
					}
					System.out.print(aktuellerStringBuilder);
					System.out.println("\nNatuerlichsprachiger Treffer? Druecken Sie j fuer 'Ja', oder ein beliebiges anderes Zeichen fuer 'Nein'.");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String antwort = new String("");
					try {
						antwort = br.readLine().toLowerCase();
						if (antwort.equals("j")) {
							weitermachen = false;
							encrypt.append("Korrekter Buchstabe: "+kryptDeutsch.charAt(j)+"\n");
							encrypt.append("Entschluesselter Text:\n"+aktuellerStringBuilder);
						} else {
							j++;
							continue;
						}
						
					} catch (IOException e) {
						System.out.println("Ungueltige Eingabe.");
					} catch (Throwable t) {
						System.out.println("Bitte nur j oder andere druckbare Zeichen eingeben!");
					}
				}
				
			} while (weitermachen);
		return encrypt.toString();
	}
	
	private List<Character> findeHaeufigstes() {
		Map<Character, Integer> map = new HashMap<>();
        List<Character> einzigartigeChars = new ArrayList<>();
        //Hashmap erstellen, Schluessel ist der Char in dem Text, Wert ist, wie oft er vorkommt
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
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
