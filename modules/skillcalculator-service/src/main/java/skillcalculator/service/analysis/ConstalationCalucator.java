package skillcalculator.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.MainCharacter;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.PetModel;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.service.analysis.model.CharacterAnalysisModel;
import skillcalculator.service.analysis.model.CharacterAnalysisModelForSkill;
import skillcalculator.service.analysis.model.ConstalationAnalysisModel;
import skillcalculator.service.analysis.util.ArrayPositionUtil;
import skillcalculator.service.analysis.util.BestConstilationListHolder;

@Component
@Scope("prototype")
public class ConstalationCalucator implements Runnable {

	private ApplicationContext context;

	private NarutoCharacter[] constalation;
	private List<PetModel> pets;
	private MainCharacter main;
	private EffektEnum[] priorityEffects;
	private int minComboValue;
	
	private List<Skill> usedSkills;

	public ConstalationCalucator(NarutoCharacter[] constalation, List<PetModel> pets, MainCharacter main, int minComboValue, EffektEnum[] priorityEffects, ApplicationContext context) {
		this.constalation = constalation;
		this.pets = pets;
		this.main = main;
		this.minComboValue = minComboValue;
		this.priorityEffects = priorityEffects;
		this.context = context;
	}

	@Override
	public void run() {
		for (int z = 0; z < 4; z++) {
			List<Skill> opakList = getSkillsOfType(main.getOpakSkillAsList(), SkillTypeEnum.OPAK);
			List<Skill> standardList = getSkillsOfType(main.getOpakSkillAsList(), SkillTypeEnum.STANDARD);
			List<Skill> passiveList = getSkillsOfType(main.getOpakSkillAsList(), SkillTypeEnum.VERFOLGEN);
			for (int i = 0; i < opakList.size(); i++)
				for (int j = 0; j < standardList.size(); j++)
					for (int k = 0; k < passiveList.size(); k++)
						for (int l = 0; l < pets.size(); l++) {
							NarutoCharacter ncMain = createNarutoCharacter(main.getName(), opakList.get(i), standardList.get(i), passiveList.get(i), pets
									.get(i).getSkill());
							NarutoCharacter[] newConstalation = ArrayPositionUtil.addCharacterToSpecificPosition(constalation, ncMain, z);
							if (proofConstelationOfPriorityEffects(newConstalation, priorityEffects)) {
								List<CharacterAnalysisModel> camList = getCombosFromConstalation(newConstalation);
								int comboCounter = 0;
								for (CharacterAnalysisModel characterAnalysisModel : camList) {
									comboCounter += characterAnalysisModel.getOpak().getComboLength();
									comboCounter += characterAnalysisModel.getStandart().getComboLength();
								}
								if (comboCounter >= minComboValue) {
									BestConstilationListHolder bclh = context.getBean(BestConstilationListHolder.class);
									bclh.addToList(new ConstalationAnalysisModel(camList.get(0), camList.get(1), camList.get(2), camList.get(3), comboCounter));
								}
							}
						}
		}
	}

	private NarutoCharacter createNarutoCharacter(String name, Skill... skills) {
		return new NarutoCharacter(name, 5, new HashSet<>(Arrays.asList(skills)));
	}

	private List<Skill> getSkillsOfType(List<Skill> skills, SkillTypeEnum skillType) {
		List<Skill> newSkills = new ArrayList<>();
		for (Skill skill : skills)
			if (skillType.equals(skill.getSkillTypeEnum()))
				newSkills.add(skill);
		return newSkills;
	}

	private boolean proofConstelationOfPriorityEffects(NarutoCharacter[] characterArray, EffektEnum[] priorityEffects) {
		boolean[] isEffectsInConstelation = new boolean[priorityEffects.length];
		for (int i = 0; i < characterArray.length; i++) {
			for (Skill skill : characterArray[i].getSkill())
				for (EffektEnum effect : skill.getEffektEnumAusloesen())
					for (int j = 0; j < priorityEffects.length; j++)
						if (effect.equals(priorityEffects[j]))
							isEffectsInConstelation[j] = true;
		}
		for (int i = 0; i < isEffectsInConstelation.length; i++)
			if (!isEffectsInConstelation[i])
				return false;
		return true;
	}

	public List<CharacterAnalysisModel> getCombosFromConstalation(NarutoCharacter... characters) {
		List<CharacterAnalysisModel> camList = new ArrayList<>();
		for (NarutoCharacter narutoCharacter : characters) {
			CharacterAnalysisModel cam = new CharacterAnalysisModel(narutoCharacter);
			cam.setOpak(new CharacterAnalysisModelForSkill(SkillTypeEnum.OPAK, getComboChainStarter(narutoCharacter,
					findSkillsWithTypeFromChar(narutoCharacter, SkillTypeEnum.OPAK).get(0), characters).toString()));
			cam.setStandart(new CharacterAnalysisModelForSkill(SkillTypeEnum.STANDARD, getComboChainStarter(narutoCharacter,
					findSkillsWithTypeFromChar(narutoCharacter, SkillTypeEnum.STANDARD).get(0), characters).toString()));
			camList.add(cam);
		}
		return camList;
	}

	private List<Skill> findSkillsWithTypeFromChar(NarutoCharacter character, SkillTypeEnum type) {
		if (SkillTypeEnum.STANDARD.equals(type)) {
			for (Skill skill : character.getSkill())
				if (skill.iseSTD())
					return Arrays.asList(skill);
		}
		List<Skill> skillList = new ArrayList<>();
		for (Skill skill : character.getSkill())
			if (type.equals(skill.getSkillTypeEnum()))
				skillList.add(skill);
		return skillList;
	}

	private StringBuilder getComboChainStarter(NarutoCharacter narutoCharacter, Skill skill, NarutoCharacter[] characters) {
		usedSkills = new ArrayList<>();
		return getComboChain(narutoCharacter, skill, characters);
	}

	private boolean compareSkill(Skill skill, Skill verfolgSkill) {
		for (EffektEnum ausloesen : skill.getEffektEnumAusloesen())
			for (EffektEnum verfolgen : verfolgSkill.getEffektEnumVerfolgen())
				if (ausloesen.equals(verfolgen) && verfolgSkill.getAnzAusloesen() > getCountOfSpecialUsedSkill(verfolgSkill))
					return true;
		return false;
	}

	private int getCountOfSpecialUsedSkill(Skill skill) {
		int timesUsed = 0;
		for (Skill usedSkill : usedSkills) {
			if (usedSkill.getName().equals(skill.getName()))
				timesUsed++;
		}
		return timesUsed;
	}

	private StringBuilder getComboChain(NarutoCharacter narutoCharacter, Skill skill, NarutoCharacter[] characters) {
		StringBuilder skillChain = new StringBuilder(skill.getName());
		for (NarutoCharacter chars : characters) {
			List<Skill> verfolgenList = findSkillsWithTypeFromChar(chars, SkillTypeEnum.VERFOLGEN);
			for (Skill verfolgSkill : verfolgenList)
				if (compareSkill(skill, verfolgSkill)) {
					usedSkills.add(verfolgSkill);
					skillChain.append(" -> " + getComboChain(chars, verfolgSkill, characters));
					return skillChain;
				}
		}
		return skillChain;
	}

}
