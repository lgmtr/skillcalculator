package skillcalculator.service.analysis.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.MainCharacter;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.repository.MainCharacterRepository;
import skillcalculator.domain.repository.PetRepository;
import skillcalculator.domain.repository.SkillRepository;
import skillcalculator.service.analysis.CharacterAnalysis;
import skillcalculator.service.analysis.model.CharacterAnalysisModel;
import skillcalculator.service.analysis.model.ConstalationAnalysisModel;
import skillcalculator.service.analysis.util.ArrayPositionUtil;
import skillcalculator.service.analysis.util.BestConstilationListHolder;
import skillcalculator.service.character.CharacterService;
import skillcalculator.service.export.ReaderFileService;

@ContextConfiguration("classpath:test-context.xml")
public class CharacterAnalysisTest extends AbstractTestNGSpringContextTests {

	@Resource
	private ReaderFileService readerFileService;

	@Resource
	private CharacterAnalysis characterAnalysis;

	@Resource
	private CharacterService characterService;

	@Resource
	private PetRepository petRepository;

	@Resource
	private SkillRepository skillRepository;

	@Resource
	private MainCharacterRepository mainRepository;

	@Resource
	private TaskExecutor taskExecutor;

	@Resource
	private BestConstilationListHolder bclh;

	@Test
	public void getCombosWithMain() {
		String fileNameChar = this.getClass().getResource("/testdata/Export_Test_Char.csv").getFile();
		File fileChar = new File(fileNameChar);
		readerFileService.readCharacter(fileChar);
		String fileNameSkill = this.getClass().getResource("/testdata/Export_Test_Skill.csv").getFile();
		File fileSkill = new File(fileNameSkill);
		readerFileService.readSkills(fileSkill);
		characterAnalysis.getBestConstalationFromAListOfCharacters(characterService.getAllCharacters(), 43, EffektEnum.HEILUNG, EffektEnum.WIEDERBELEBUNG);
		ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) taskExecutor;
		do {
		} while (threadPoolTaskExecutor.getActiveCount() > 1);
		List<ConstalationAnalysisModel> bestComboConstList = new ArrayList<>(bclh.getList());
		System.out.println("Done!!! List size is: " +  bestComboConstList.size());
		for (ConstalationAnalysisModel nc : bestComboConstList) {
			System.out.println(nc.toString());
			List<CharacterAnalysisModel> camList = characterAnalysis.getCombosFromConstalation(nc.getFirstCharacter().getCharacter(), nc.getSecondCharacter()
					.getCharacter(), nc.getThirdCharacter().getCharacter(), nc.getFourthCharacter().getCharacter());
			for (CharacterAnalysisModel characterAnalysisModel : camList) {
				System.out.println("Name: " + characterAnalysisModel.getCharacter().getName() + "\tStars: " + characterAnalysisModel.getCharacter().getStars());
				System.out.println("Opak Combo: " + characterAnalysisModel.getOpak().getSkillChain());
				System.out.println("Standard Combo: " + characterAnalysisModel.getStandart().getSkillChain());
			}
			System.out.println("============================================================================================================");
		}
	}

	@Test
	public void arrayPositionTest() {
		String fileNameChar = this.getClass().getResource("/testdata/Export_Test_Char.csv").getFile();
		File fileChar = new File(fileNameChar);
		readerFileService.readCharacter(fileChar);
		String fileNameSkill = this.getClass().getResource("/testdata/Export_Test_Skill.csv").getFile();
		File fileSkill = new File(fileNameSkill);
		readerFileService.readSkills(fileSkill);
		NarutoCharacter naruto = characterService.findCharacterWithName("Naruto_2");
		NarutoCharacter sakura = characterService.findCharacterWithName("Sakura_1");
		NarutoCharacter sasuke = characterService.findCharacterWithName("Sasuke_1");
		NarutoCharacter ncMain = characterService.getMainAsNarutoCharacter("Nachtklinge", "Chidory-Klinge: Gleitender Shatten", "Tai-Jutsu-Angriff",
				"Iai Giri", "Gamaken");
		NarutoCharacter[] nChars = { naruto, sakura, sasuke };
		NarutoCharacter[] nCharsWithMain = ArrayPositionUtil.addCharacterToSpecificPosition(nChars, ncMain, 0);
		System.out.println(nCharsWithMain[0].getName() + " | " + nCharsWithMain[1].getName() + " | " + nCharsWithMain[2].getName() + " | "
				+ nCharsWithMain[3].getName());
		nCharsWithMain = ArrayPositionUtil.addCharacterToSpecificPosition(nChars, ncMain, 1);
		System.out.println(nCharsWithMain[0].getName() + " | " + nCharsWithMain[1].getName() + " | " + nCharsWithMain[2].getName() + " | "
				+ nCharsWithMain[3].getName());
		nCharsWithMain = ArrayPositionUtil.addCharacterToSpecificPosition(nChars, ncMain, 2);
		System.out.println(nCharsWithMain[0].getName() + " | " + nCharsWithMain[1].getName() + " | " + nCharsWithMain[2].getName() + " | "
				+ nCharsWithMain[3].getName());
		nCharsWithMain = ArrayPositionUtil.addCharacterToSpecificPosition(nChars, ncMain, 3);
		System.out.println(nCharsWithMain[0].getName() + " | " + nCharsWithMain[1].getName() + " | " + nCharsWithMain[2].getName() + " | "
				+ nCharsWithMain[3].getName());
	}

	@Test
	public void mainCharacterTest() {
		MainCharacter main = mainRepository.findAll().get(0);
		for (Skill skill : main.getOpakSkill()) {
			System.out.println(skill.getName());
		}
		for (Skill skill : main.getStandardSkill()) {
			System.out.println(skill.getName());
		}
	}

	@Test
	public void getAllMainChars() {
		NarutoCharacter ncMain = characterService.getMainAsNarutoCharacter("Nachtklinge", "Chidory-Klinge: Gleitender Shatten", "Tai-Jutsu-Angriff",
				"Iai Giri", "Gamaken");
		System.out.println("Name: " + ncMain.getName());
		for (Skill skill : ncMain.getSkill())
			System.out.println("Skill: " + skill.getName());
	}

}
