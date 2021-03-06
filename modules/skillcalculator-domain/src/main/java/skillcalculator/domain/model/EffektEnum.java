package skillcalculator.domain.model;

public enum EffektEnum {
	TS("Tiefes Schweben", "C", "", ""),
	HS("Hohes Schweben", "C", "", ""),
	F("Fallen", "C", "", ""),
	Z("Zur�ckschlagen", "C", "", ""),
	C5("5er-Combo", "C", "", ""),
	C10("5er-Combo", "C", "", ""),
	VERGIFTEN("f�gt Schaden zu, kann 4 mal addiert werden", "N", "", "1+"),
	ENTFLAMMEN("f�gt Schaden zu, Verhindert Heilung", "N", "", "1"),
	SCHLAF("kann durch Tai-Jutsu vor Rundenende aufgehoben werden", "N", "", "1"),
	LAEHMUNG("Verhindert Verfolgung", "N", "", "1"),
	BLINDHEIT("Verhindert Standart-Attacken", "N", "", "1"),
	SIEGELPUNKT("Verhindert Opak-Verwendung f�r 3 Runden", "N", "", "3"),
	VERWIRRUNG("Verhindert das einsetzten von Opaken, l�sst betroffenen Ninja Verb�ndete angreifen", "N", "", "1"),
	HIMMELSVERLIES("Betroffener Ninja erleidet bei der Verwendung seines Opak's Schaden", "N", "", "1"),
	MARKIERUNG("-30% Verteidigung, markiert, Gelegentlich Opak-Vorraussetzung", "N", "", "1"),
	UNTERBRECHUNG("Verhindert Opak-Verwendung f�r 1 Runden", "N", "", "1"),
	HEILUNG("Heilt sich oder Verb�ndete", "N", "", "1"),
	WIEDERBELEBUNG("Belebebt einen Verb�ndeten wieder", "N", "", "1"),
	AUFERSTEHUNG("Wiederauferstehung von den Toten", "N", "", "1");
	
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
	
	public static EffektEnum getEnumWithName(String name) {
		for(EffektEnum effektEnum : EffektEnum.values())
			if(effektEnum.name().equals(name))
				return effektEnum;
		return null;
	}
}
