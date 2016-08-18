package skillcalculator.service.character.test;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.service.character.CharacterService;

@ContextConfiguration("classpath:test-context.xml")
public class CharacterServiceTest extends AbstractTestNGSpringContextTests {

	@Resource
	private CharacterService characterService;

	@Test
	public void characterCreationTest() {
		Skill skill1 = characterService.createSkill("Schattendoppelgänger-Jutsu",
				"Bringt 1 Schattendoppelgänger hervor, die Attribute des Schattendoppelgängers werden von denen Narutos beeinflusst.", SkillTypeEnum.OPAK,
				characterService.createEffektSet(), characterService.createEffektSet());
		Skill skill2 = characterService.createSkill("Tai-Jutsu-Angriff",
				"Greift den direkt gegenüberstehenden Gegner an, löst mit einer giwissen Wahrscheinlichkeit eine 3er-Combo aus und verursacht "
						+ "Tiefes Schweben.", SkillTypeEnum.STANDART, characterService.createEffektSet(), characterService.createEffektSet(EffektEnum.TS));
		Skill skill3 = characterService.createSkill("Oiroke-no-Jutsu",
				"Wird zu Kampfbeginn angewendet, vermindert den Widerstand von bis zu 4 männlichen Gegnern.", SkillTypeEnum.PASSIV,
				characterService.createEffektSet(), characterService.createEffektSet());
		Skill skill4 = characterService.createSkill("Schattendoppelgänger-Sperrfeuer",
				"Ersetzt den Standardangriff, wenn Schattendoppelgänger auf dem Feld sind, greift mit den Schattendoppelgängern gemeinsam an, "
						+ "trifft immer und verursacht mit einer gewissen Wahrscheinlichkeit tiefes Schweben.", SkillTypeEnum.PASSIV,
				characterService.createEffektSet(), characterService.createEffektSet());
		Set<Skill> skills = characterService.createSkills(skill1, skill2, skill3, skill4);
		characterService.addCharacter("Naruto", 1, skills);
		List<NarutoCharacter> characterList = characterService.getAllCharacters();
		for (NarutoCharacter narutoCharacter : characterList) {
			System.out.println(narutoCharacter.getName());
		}
	}

}
