package skillcalculator.service.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.service.character.CharacterService;

@Service
public class WriteFileService implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_SEPERATOR = ";";

	private static final String DEFAULT_QUOTE = "\"";

	@Resource
	private CharacterService characterService;

	public void writeEntriesToFile(String fileName) {
		try {
			FileWriter writerChar = new FileWriter(fileName + "_Char.csv");
			FileWriter writerSkill = new FileWriter(fileName + "_Skill.csv");
			writeHeader(writerChar, writerSkill);
			List<NarutoCharacter> narutoCharacters = characterService.getAllCharacters();
			for (NarutoCharacter narutoCharacter : narutoCharacters)
				writeLineChar(writerChar, narutoCharacter);
			for (Skill skill : characterService.getAllSkills())
				writeLineSkill(writerSkill, skill);
			writerChar.flush();
			writerChar.close();
			writerSkill.flush();
			writerSkill.close();
		} catch (IOException e) {
			System.err.println("FEHLER");
			e.printStackTrace();
		}

	}

	private void writeLineSkill(Writer writerSkill, Skill skill) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(DEFAULT_QUOTE + skill.getCharacter().getId() + DEFAULT_QUOTE);
		sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + skill.getName() + DEFAULT_QUOTE);
		sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + skill.getBeschreibung() + DEFAULT_QUOTE);
		sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + skill.getSkillTypeEnum().name() + DEFAULT_QUOTE);
		sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + skill.getAnzAusloesen() + DEFAULT_QUOTE);
		int counter = 0;
		for (EffektEnum effektEnum : skill.getEffektEnumVerfolgen()) {
			sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + effektEnum.name() + DEFAULT_QUOTE);
			counter++;
		}
		for (int i = counter; i < 3; i++) 
			sb.append(DEFAULT_SEPERATOR);
		counter = 0;
		for (EffektEnum effektEnum : skill.getEffektEnumAusloesen()) {
			sb.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + effektEnum.name() + DEFAULT_QUOTE);
			counter++;
		}
		for (int i = counter; i < 3; i++) 
			sb.append(DEFAULT_SEPERATOR);
		sb.append("\n");
		writerSkill.append(sb.toString());
	}

	private void writeLineChar(Writer w, NarutoCharacter narutoCharacter) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(DEFAULT_QUOTE + narutoCharacter.getName() + DEFAULT_QUOTE + DEFAULT_SEPERATOR + DEFAULT_QUOTE + narutoCharacter.getStars() + DEFAULT_QUOTE);
		sb.append("\n");
		w.append(sb.toString());
	}

	private void writeHeader(Writer wC, Writer wS) throws IOException {
		StringBuilder sbC = new StringBuilder();
		boolean firstEntry = true;
		for (WriterHelperEnum writerHelperEnum : WriterHelperEnum.getListWithIdent("C")) {
			if (firstEntry) {
				sbC.append(DEFAULT_QUOTE + writerHelperEnum.name() + DEFAULT_QUOTE);
				firstEntry = false;
			} else {
				sbC.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + writerHelperEnum.name() + DEFAULT_QUOTE);
			}
		}
		sbC.append("\n");
		wC.append(sbC.toString());
		StringBuilder sbS = new StringBuilder();
		firstEntry = true;
		for (WriterHelperEnum writerHelperEnum : WriterHelperEnum.getListWithIdent("S")) {
			if (firstEntry) {
				sbS.append(DEFAULT_QUOTE + writerHelperEnum.name() + DEFAULT_QUOTE);
				firstEntry = false;
			} else {
				sbS.append(DEFAULT_SEPERATOR + DEFAULT_QUOTE + writerHelperEnum.name() + DEFAULT_QUOTE);
			}
		}
		sbS.append("\n");
		wS.append(sbS.toString());
	}

}
