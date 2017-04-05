package skillcalculator.domain.model;

public enum SkillTypeEnum {
	OPAK, STANDARD, VERFOLGEN, PASSIV, GEIST;

	public static SkillTypeEnum getEnumWithName(String name){
		for(SkillTypeEnum skillTypeEnum : SkillTypeEnum.values())
			if(skillTypeEnum.name().equals(name))
				return skillTypeEnum;
		return null;
	}
}
