package skillcalculator.domain.test;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.domain.repository.NarutoCharacterRepository;
import skillcalculator.domain.repository.SkillRepository;

@ContextConfiguration("classpath:test-context.xml")
public class SimleDBTest extends AbstractTestNGSpringContextTests {

	@Resource
	private NarutoCharacterRepository narutoCharacterRepository;
	
	@Resource
	private SkillRepository skillRepository;

	@Test
	public void createNaruto() {
		NarutoCharacter naruto = new NarutoCharacter("Naruto", 1);
		Skill skill1 = new Skill();
		skill1.setName("Schattendoppelgänger-Jutsu");
		skill1.setSkillTypeEnum(SkillTypeEnum.OPAK);
		skill1.setBeschreibung("Bringt 1 Schattendoppelgänger hervor, die Attribute des Schattendoppelgängers werden von "
				+ "denen Narutos beeinflusst.");
		skill1.setCharacter(naruto);
		Skill skill2 = new Skill();
		skill2.setName("Tai-Jutsu-Angriff");
		skill2.setSkillTypeEnum(SkillTypeEnum.STANDARD);
		Set<EffektEnum> effektSetS1 = new HashSet<EffektEnum>();
		effektSetS1.add(EffektEnum.TS);
		skill2.setEffektEnumAusloesen(effektSetS1);
		skill2.setBeschreibung("Greift den direkt gegenüberstehenden Gegner an, löst mit einer giwissen Wahrscheinlichkeit "
				+ "eine 3er-Combo aus und verursacht Tiefes Schweben.");
		skill2.setCharacter(naruto);
		Skill skill3 = new Skill();
		skill3.setName("Oiroke-no-Jutsu");
		skill3.setSkillTypeEnum(SkillTypeEnum.PASSIV);
		skill3.setBeschreibung("Wird zu Kampfbeginn angewendet, vermindert den Widerstand von bis zu 4 männlichen Gegnern.");
		skill3.setCharacter(naruto);
		Skill skill4 = new Skill();
		skill4.setName("Schattendoppelgänger-Sperrfeuer");
		skill4.setSkillTypeEnum(SkillTypeEnum.PASSIV);
		skill4.setBeschreibung("Ersetzt den Standardangriff, wenn Schattendoppelgänger auf dem Feld sind, greift mit den "
				+ "Schattendoppelgängern gemeinsam an, trifft immer und verursacht mit einer gewissen Wahrscheinlichkeit tiefes Schweben.");
		skill4.setCharacter(naruto);
		Set<Skill> skills = new HashSet<Skill>();
		skills.add(skill1);
		skills.add(skill2);
		skills.add(skill3);
		skills.add(skill4);
		naruto.setSkill(skills);
		narutoCharacterRepository.save(naruto);
		skillRepository.save(skills);
	}

}
