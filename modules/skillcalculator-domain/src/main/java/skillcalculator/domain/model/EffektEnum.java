package skillcalculator.domain.model;

public enum EffektEnum {
	TS("Tiefes Schweben", "C", "", ""),
	HS("Hohes Schweben", "C", "", ""),
	F("Fallen", "C", "", ""),
	Z("Zurückschlagen", "C", "", ""),
	VERGIFTEN("fügt Schaden zu, kann 4 mal addiert werden", "N", "", "1+"),
	ENTFLAMMEN("fügt Schaden zu, Verhindert Heilung", "N", "", "1"),
	SCHLAF("kann durch Tai-Jutsu vor Rundenende aufgehoben werden", "N", "", "1"),
	LAEHMUNG("Verhindert Verfolgung", "N", "", "1"),
	BLINDHEIT("Verhindert Standart-Attacken", "N", "", "1"),
	SIEGELPUNKT("Verhindert Opak-Verwendung für 3 Runden", "N", "", "3"),
	VERWIRRUNG("Verhindert das einsetzten von Opaken, lässt betroffenen Ninja Verbündete angreifen", "N", "", "1"),
	HIMMELSVERLIES("Betroffener Ninja erleidet bei der Verwendung seines Opak's Schaden", "N", "", "1"),
	MARKIERUNG("-30% Verteidigung, markiert, Gelegentlich Opak-Vorraussetzung", "N", "", "1");
	
	private String desc;

	private String type;
	
	private String bonus;
	
	private String dauer;
	
	private EffektEnum(String desc, String type, String bonus, String dauer) {
		this.desc = desc;
		this.type = type;
		this.bonus = bonus;
		this.dauer = dauer;
	}

	public String getDesc() {
		return desc;
	}

	public String getType() {
		return type;
	}

	public String getBonus() {
		return bonus;
	}
	
	public String getDauer(){
		return dauer;
	}
}
