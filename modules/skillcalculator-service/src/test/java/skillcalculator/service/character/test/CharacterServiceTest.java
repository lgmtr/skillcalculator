package skillcalculator.service.character.test;

import java.io.File;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.service.character.CharacterService;
import skillcalculator.service.export.ReaderFileService;
import skillcalculator.service.export.WriteFileService;

@ContextConfiguration("classpath:test-context.xml")
public class CharacterServiceTest extends AbstractTestNGSpringContextTests {

	@Resource
	private CharacterService characterService;

	@Resource
	private WriteFileService writeFileService;

	@Resource
	private ReaderFileService readerFileService;

	public void characterCreationTest() {
		Skill skillN1 = characterService.createSkill("Schattendoppelg�nger-Jutsu",
				"Bringt 1 Schattendoppelg�nger hervor, die Attribute des Schattendoppelg�ngers werden von denen Narutos beeinflusst.", SkillTypeEnum.OPAK,
				characterService.createEffektSet(), characterService.createEffektSet(), 1);
		Skill skillN2 = characterService.createSkill("Tai-Jutsu-Angriff",
				"Greift den direkt gegen�berstehenden Gegner an, l�st mit einer giwissen Wahrscheinlichkeit eine 3er-Combo aus und verursacht "
						+ "Tiefes Schweben.", SkillTypeEnum.STANDARD, characterService.createEffektSet(), characterService.createEffektSet(EffektEnum.TS), 1);
		Skill skillN3 = characterService.createSkill("Oiroke-no-Jutsu",
				"Wird zu Kampfbeginn angewendet, vermindert den Widerstand von bis zu 4 m�nnlichen Gegnern.", SkillTypeEnum.PASSIV,
				characterService.createEffektSet(), characterService.createEffektSet(), 1);
		Skill skillN4 = characterService.createSkill("Schattendoppelg�nger-Sperrfeuer",
				"Ersetzt den Standardangriff, wenn Schattendoppelg�nger auf dem Feld sind, greift mit den Schattendoppelg�ngern gemeinsam an, "
						+ "trifft immer und verursacht mit einer gewissen Wahrscheinlichkeit tiefes Schweben.", SkillTypeEnum.PASSIV,
				characterService.createEffektSet(), characterService.createEffektSet(EffektEnum.TS), 1);
		Set<Skill> skillsN = characterService.createSkills(skillN1, skillN2, skillN3, skillN4);
		characterService.addCharacter("Naruto", 1, skillsN);
		Skill skillS1 = characterService.createSkill("Feuerversteck: Flammende Feuerkugel", "F�gt bis zu 4 Gegnern Nin-Jutsu-Schaden zu", SkillTypeEnum.OPAK,
				characterService.createEffektSet(), characterService.createEffektSet(), 1);
		Skill skillS2 = characterService.createSkill("Tai-Jutsu-Angriff",
				"Greift den direkt gegen�berstehenden Gegner an, l�st mit einer giwissen Wahrscheinlichkeit eine 3er-Combo aus und verursacht "
						+ "Tiefes Schweben.", SkillTypeEnum.STANDARD, characterService.createEffektSet(), characterService.createEffektSet(EffektEnum.TS), 1);
		Skill skillS3 = characterService.createSkill("Zusammenspielende L�wen",
				"Verfolgt von tiefem Schweben betroffene Gegner und verursacht Zur�ckschlagen, Gegner hinter zur�ckgeschlagenen Ninjas erleiden Schaden.",
				SkillTypeEnum.VERFOLGEN, characterService.createEffektSet(EffektEnum.TS), characterService.createEffektSet(EffektEnum.Z), 1);
		Skill skillS4 = characterService.createSkill("Feuerversteck: Kunst der Phoenixblume: Verfolgung",
				"Wird ab 10er-Combo ausgel�st und f�gt 2 zuf�lligen Gegnern Nin-Jutsu-Schaden zu.", SkillTypeEnum.VERFOLGEN,
				characterService.createEffektSet(EffektEnum.C10), characterService.createEffektSet(), 1);
		Set<Skill> skillsS = characterService.createSkills(skillS1, skillS2, skillS3, skillS4);
		characterService.addCharacter("Sasuke", 1, skillsS);
	}

	@Test
	public void writeFileTest() {
		characterCreationTest();
		writeFileService.writeEntriesToFile(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "Export_Test");
	}

	@Test
	public void readFileTest() {
		String fileNameChar = this.getClass().getResource("/testdata/Export_Test_Char.csv").getFile();
		File fileChar = new File(fileNameChar);
		readerFileService.readCharacter(fileChar);
		String fileNameSkill = this.getClass().getResource("/testdata/Export_Test_Skill.csv").getFile();
		File fileSkill = new File(fileNameSkill);
		readerFileService.readSkills(fileSkill);
	}
}
