package skillcalculator.service.analysis.model;

import skillcalculator.domain.model.SkillTypeEnum;

public class CharacterAnalysisModelForSkill {
	
	private SkillTypeEnum skillType;
	
	private String skillChain;
	
	private int comboLength;
	
	public CharacterAnalysisModelForSkill(SkillTypeEnum skillType, String skillChain) {
		this.skillType = skillType;
		this.skillChain = skillChain;
		this.setComboLength(skillChain.split(" -> ").length);
	}

	public SkillTypeEnum getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillTypeEnum skillType) {
		this.skillType = skillType;
	}

	public String getSkillChain() {
		return skillChain;
	}

	public void setSkillChain(String skillChain) {
		this.skillChain = skillChain;
	}

	public int getComboLength() {
		return comboLength;
	}

	public void setComboLength(int comboLength) {
		this.comboLength = comboLength;
	}

}
