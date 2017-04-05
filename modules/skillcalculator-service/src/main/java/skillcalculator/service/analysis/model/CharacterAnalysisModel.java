package skillcalculator.service.analysis.model;

import skillcalculator.domain.model.NarutoCharacter;

public class CharacterAnalysisModel {
	
	private NarutoCharacter character;
	
	private CharacterAnalysisModelForSkill opak;
	
	private CharacterAnalysisModelForSkill standart;

	public CharacterAnalysisModel(NarutoCharacter character) {
		this.character = character;
	}
	
	public NarutoCharacter getCharacter() {
		return character;
	}

	public void setCharacter(NarutoCharacter character) {
		this.character = character;
	}

	public CharacterAnalysisModelForSkill getOpak() {
		return opak;
	}

	public void setOpak(CharacterAnalysisModelForSkill opak) {
		this.opak = opak;
	}

	public CharacterAnalysisModelForSkill getStandart() {
		return standart;
	}

	public void setStandart(CharacterAnalysisModelForSkill standart) {
		this.standart = standart;
	}

	
}
