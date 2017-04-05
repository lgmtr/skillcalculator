package skillcalculator.domain.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.MainCharacter;
import skillcalculator.domain.model.PetModel;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.domain.repository.MainCharacterRepository;
import skillcalculator.domain.repository.PetRepository;
import skillcalculator.domain.repository.SkillRepository;

@Service
public class StartUpService {

	@Resource
	private MainCharacterRepository mainCharacterRepository;

	@Resource
	private SkillRepository skillRepository;

	@Resource
	private PetRepository petRepository;

	@PostConstruct
	private void init() {
		addBlitzMain();
		addPets();
	}

	private void addPets() {
		addPet("Gamaken", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.Z)), new HashSet<>(Arrays.asList(EffektEnum.F)), 2);
		addPet("Yata-Krähe", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.HS)), new HashSet<>(Arrays.asList(EffektEnum.TS)), 2);
		addPet("Affenkönig", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.HS)), new HashSet<>(Arrays.asList(EffektEnum.F)), 2);
		addPet("Riesenschlange", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.F)), new HashSet<>(Arrays.asList(EffektEnum.HS)), 2);
		addPet("Drachenhai", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.TS)), new HashSet<>(Arrays.asList(EffektEnum.HS)), 2);
	}

	private void addPet(String name, String description, SkillTypeEnum type, Set<EffektEnum> effektVerfolgen, Set<EffektEnum> effektAusloesen, int anzAusloesen) {
		Skill skill = createSkill(name + " Attacke", description, type, effektVerfolgen, effektAusloesen, anzAusloesen);
		petRepository.save(new PetModel(name, skillRepository.save(skill)));
	}

	private void addBlitzMain() {
		Skill opak1 = createSkill("Siegelblitz-Hieb", "", SkillTypeEnum.OPAK, new HashSet<>(), new HashSet<>(Arrays.asList(EffektEnum.SIEGELPUNKT)), 1);
		Skill opak2 = createSkill("Chidory-Klinge: Gleitender Shatten", "", SkillTypeEnum.OPAK, new HashSet<>(),
				new HashSet<>(Arrays.asList(EffektEnum.LAEHMUNG, EffektEnum.F)), 1);
		Skill opak3 = createSkill("Blitzversteck-Rüstung", "", SkillTypeEnum.OPAK, new HashSet<>(), new HashSet<>(Arrays.asList()), 1);
		Skill opak4 = createSkill("Iaido: Sofort-Blitz", "", SkillTypeEnum.OPAK, new HashSet<>(),
				new HashSet<>(Arrays.asList(EffektEnum.MARKIERUNG, EffektEnum.LAEHMUNG)), 1);
		Skill standard1 = createSkill("Tai-Jutsu-Angriff", "", SkillTypeEnum.STANDARD, new HashSet<>(), new HashSet<>(Arrays.asList(EffektEnum.Z)), 1);
		Skill standard2 = createSkill("Ausweichen", "", SkillTypeEnum.STANDARD, new HashSet<>(), new HashSet<>(Arrays.asList(EffektEnum.F)), 1);
		Skill standard3 = createSkill("Donnermeuchelschwert", "", SkillTypeEnum.STANDARD, new HashSet<>(), new HashSet<>(Arrays.asList(EffektEnum.HS)), 1);
		Skill standard4 = createSkill("Aufmerksamkeit", "", SkillTypeEnum.STANDARD, new HashSet<>(), new HashSet<>(Arrays.asList()), 1);
		Skill passiv1 = createSkill("Iai Giri", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.Z)),
				new HashSet<>(Arrays.asList(EffektEnum.F)), 1);
		Skill passiv2 = createSkill("Scharfer Chidori-Speer", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.HS)),
				new HashSet<>(Arrays.asList(EffektEnum.LAEHMUNG, EffektEnum.C10, EffektEnum.TS)), 1);
		Skill passiv3 = createSkill("Blitz-Narakumi", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.F)),
				new HashSet<>(Arrays.asList(EffektEnum.TS)), 1);
		Skill passiv4 = createSkill("Sichelmond-Hieb", "", SkillTypeEnum.VERFOLGEN, new HashSet<>(Arrays.asList(EffektEnum.TS)),
				new HashSet<>(Arrays.asList(EffektEnum.Z)), 1);
		Set<Skill> opaks = new HashSet<>(Arrays.asList(opak1, opak2, opak3, opak4));
		Set<Skill> standardSkills = new HashSet<>(Arrays.asList(standard1, standard2, standard3, standard4));
		Set<Skill> passiveSkillsOne = new HashSet<>(Arrays.asList(passiv1, passiv2, passiv3, passiv4));
		MainCharacter blitzMain = new MainCharacter("Nachtklinge", opaks, standardSkills, passiveSkillsOne, new HashSet<>(), new HashSet<>());
		MainCharacter main = mainCharacterRepository.save(blitzMain);
		for (Skill skill : passiveSkillsOne)
			skill.setMain(main);
		for (Skill skill : standardSkills)
			skill.setMain(main);
		for (Skill skill : opaks)
			skill.setMain(main);
		skillRepository.save(opaks);
		skillRepository.save(standardSkills);
		skillRepository.save(passiveSkillsOne);

	}

	private Skill createSkill(String name, String beschreibung, SkillTypeEnum skillType, Set<EffektEnum> effektVerfolgen, Set<EffektEnum> effektAusloesen,
			int anzAusloesen) {
		Skill newSkill = new Skill();
		newSkill.setName(name);
		newSkill.setBeschreibung(beschreibung);
		newSkill.setSkillTypeEnum(skillType);
		newSkill.setEffektEnumAusloesen(effektAusloesen);
		newSkill.setEffektEnumVerfolgen(effektVerfolgen);
		newSkill.setAnzAusloesen(anzAusloesen);
		return newSkill;
	}

}
