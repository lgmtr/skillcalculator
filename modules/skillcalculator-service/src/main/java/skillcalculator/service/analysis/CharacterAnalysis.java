package skillcalculator.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.domain.repository.MainCharacterRepository;
import skillcalculator.domain.repository.NarutoCharacterRepository;
import skillcalculator.domain.repository.PetRepository;
import skillcalculator.domain.repository.SkillRepository;
import skillcalculator.service.analysis.model.CharacterAnalysisModel;
import skillcalculator.service.analysis.model.CharacterAnalysisModelForSkill;
import skillcalculator.service.analysis.util.BestConstilationListHolder;
import skillcalculator.service.analysis.util.PermutationsOld;
import skillcalculator.service.character.CharacterService;

@Service
public class CharacterAnalysis {

	@Resource
	private NarutoCharacterRepository narutoCharacterRepository;

	@Resource
	private SkillRepository skillRepository;

	@Resource
	private MainCharacterRepository mainCharacterRepository;

	@Resource
	private PetRepository petRepository;

	@Resource
	private CharacterService characterService;

	@Resource
	private CalculateCombinationsWithMain calcCombWithMain;
	
	@Resource
	private BestConstilationListHolder bclh;

	private List<Skill> usedSkills;

	public NarutoCharacter[] findBestConstalationForCombos(NarutoCharacter... characters) {
		PermutationsOld<NarutoCharacter> perm = new PermutationsOld<>(characters);
		int bestValue = 0;
		NarutoCharacter[] bestConst = { null, null, null, null };
		while (perm.hasNext()) {
			NarutoCharacter[] characterArray = perm.next();
			List<CharacterAnalysisModel> camList = getCombosFromConstalation(characterArray);
			int comboCounter = 0;
			for (CharacterAnalysisModel characterAnalysisModel : camList) {
				comboCounter += characterAnalysisModel.getOpak().getComboLength();
				comboCounter += characterAnalysisModel.getStandart().getComboLength();
			}
			if (comboCounter > bestValue) {
				bestValue = comboCounter;
				bestConst[0] = characterArray[0];
				bestConst[1] = characterArray[1];
				bestConst[2] = characterArray[2];
				bestConst[3] = characterArray[3];
			}
		}
		return bestConst;
	}

	public void getBestConstalationFromAListOfCharacters(List<NarutoCharacter> completeList, int minComboValue,
			EffektEnum... priorityEffects) {
		getBestConstalationFromAListOfCharacters(completeList, minComboValue, false, priorityEffects);
	}

	public void getBestConstalationFromAListOfCharacters(List<NarutoCharacter> completeList, int minComboValue, boolean withMain,
			EffektEnum... priorityEffects) {
		int listSize = completeList.size();
		System.out.println("Complete List Size: " + listSize);
		System.out.println("Calculation Started!!!");
		List<NarutoCharacter[]> constilationList = new ArrayList<>();
		printAllKLengthRec(completeList.toArray(new NarutoCharacter[completeList.size()]), new NarutoCharacter[3], completeList.size(), 3, constilationList,
				minComboValue, withMain, priorityEffects);
		bclh.clearList();
		calcCombWithMain.calculateConstalation(constilationList, petRepository.findAll(), mainCharacterRepository.findByName("Nachtklinge"), minComboValue,
				priorityEffects);
	}

	private void printAllKLengthRec(NarutoCharacter set[], NarutoCharacter[] prefix, int n, int k, List<NarutoCharacter[]> constilationList, int minComboValue,
			boolean withMain, EffektEnum... priorityEffects) {
		if (k == 0) {
			constilationList.add(prefix);
			return;
		}
		for (int i = 0; i < n; ++i) {
			if (!proofOfExistence(prefix, set[i])) {
				prefix[k - 1] = set[i];
				printAllKLengthRec(set, prefix, n, k - 1, constilationList, minComboValue, withMain, priorityEffects);
			}
		}
	}

	private boolean proofOfExistence(NarutoCharacter[] prefix, NarutoCharacter narutoCharacter) {
		boolean isInPrefix = false;
		for (int i = 0; i < prefix.length; i++)
			if (prefix[i] != null)
				if (prefix[i].getName().equals(narutoCharacter.getName()))
					isInPrefix = true;
		return isInPrefix;
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

	private StringBuilder getComboChainStarter(NarutoCharacter narutoCharacter, Skill skill, NarutoCharacter[] characters) {
		usedSkills = new ArrayList<>();
		return getComboChain(narutoCharacter, skill, characters);
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
}
