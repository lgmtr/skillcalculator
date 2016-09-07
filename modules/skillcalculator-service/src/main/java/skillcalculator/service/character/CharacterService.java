package skillcalculator.service.character;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.domain.repository.NarutoCharacterRepository;
import skillcalculator.domain.repository.SkillRepository;

@Service
public class CharacterService {

	@Resource
	private NarutoCharacterRepository narutoCharacterRepository;

	@Resource
	private SkillRepository skillRepository;

	public List<NarutoCharacter> getAllCharacters() {
		return narutoCharacterRepository.findAll();
	}
	
	public List<Skill> getAllSkills(){
		return skillRepository.findAll();
	}

	public void addCharacter(String name, int stars, Set<Skill> skills) {
		NarutoCharacter newCharacter = new NarutoCharacter(name, stars);
		for (Skill skill : skills)
			skill.setCharacter(newCharacter);
		newCharacter.setSkill(skills);
		narutoCharacterRepository.save(newCharacter);
		skillRepository.save(skills);
	}
	
	public Set<Skill> createSkills(Skill... skills){
		Set<Skill> skillSet = new HashSet<>();
		for (Skill skill : skills) 
			skillSet.add(skill);
		return skillSet;
	}

	public Skill createSkill(String name, String beschreibung, SkillTypeEnum skillType, Set<EffektEnum> effektVerfolgen, Set<EffektEnum> effektAusloesen, int anzAusloesen) {
		Skill newSkill = new Skill();
		newSkill.setName(name);
		newSkill.setBeschreibung(beschreibung);
		newSkill.setSkillTypeEnum(skillType);
		newSkill.setEffektEnumAusloesen(effektAusloesen);
		newSkill.setEffektEnumVerfolgen(effektVerfolgen);
		newSkill.setAnzAusloesen(anzAusloesen);
		return newSkill;
	}
	
	public void addCharReader(NarutoCharacter narutoCharacter){
		narutoCharacterRepository.save(narutoCharacter);
	}
	
	public void addSkillReader(Skill skill){
		skillRepository.save(skill);
	}
	
	public NarutoCharacter findCharacterWithName(String name){
		return narutoCharacterRepository.findOne(name);
	}
	
	public Set<EffektEnum> createEffektSet(EffektEnum... effektEnums){
		Set<EffektEnum> effektEnumSet = new HashSet<>();
		for (EffektEnum effektEnum : effektEnums) 
			if(effektEnum != null)
				effektEnumSet.add(effektEnum);
		return effektEnumSet;
	}

}
