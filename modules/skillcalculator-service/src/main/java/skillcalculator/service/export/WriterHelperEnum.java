package skillcalculator.service.export;

import java.util.ArrayList;
import java.util.List;

public enum WriterHelperEnum {
	NAME("C"),
	STARS("C"),
	CHARID("S"),
	SKILLNAME("S"),
	BESCHREIBUNG("S"),
	SKILLTYPE("S"),
	ANZ_AUSLOESEN("S"),
	VERFOLGEN_1("S"),
	VERFOLGEN_2("S"),
	VERFOLGEN_3("S"),
	AUSLOESEN_1("S"),
	AUSLOESEN_2("S"),
	AUSLOESEN_3("S");
	
	private String ident;
	
	private WriterHelperEnum(String ident) {
		this.setIdent(ident);
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	public static List<WriterHelperEnum> getListWithIdent(String ident){
		List<WriterHelperEnum> helperEnumList = new ArrayList<>();
		for(WriterHelperEnum writerHelperEnum : WriterHelperEnum.values())
			if(writerHelperEnum.ident.equals(ident))
				helperEnumList.add(writerHelperEnum);
		return helperEnumList;
	}
}
