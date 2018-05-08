package kodierung;

public class Kodierung {
	private boolean chiffrieren;
	private byte schluessel;
	private String startText;
	private String endText;
	
	// Standart-Konstruktor
	public Kodierung(boolean c, byte s, String sT, String eT) {
		this.chiffrieren = c;
		this.schluessel = s;
		this.startText = sT;
		this.endText = eT;
	}
	
	
	public void verschluessle() {
		
		
		
	}
	
	public void entschluessleMitSchluessel() {
		
		
		
	}
	public void entschluessleOhneSchluessel() {
		
		
	}
	
	
	public String toString() {
		String ausgabe = new String("");
		
		
		
		
	
		return ausgabe;
	}


	public byte getSchluessel() {
		return schluessel;
	}


	public String getStartText() {
		return startText;
	}


	public String getEndText() {
		return endText;
	}
	
	
}
