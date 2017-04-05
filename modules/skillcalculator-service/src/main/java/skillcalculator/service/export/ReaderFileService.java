package skillcalculator.service.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;
import skillcalculator.service.character.CharacterService;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ReaderFileService implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final char DEFAULT_SEPERATOR = ';';

	private static final char DEFAULT_QUOTE = '"';

	@Resource
	private CharacterService characterService;

	public void readCharacter(File file) {
		try {
			final BufferedReader in = new BufferedReader(new FileReader(file));
			CSVReader reader = new CSVReader(in, DEFAULT_SEPERATOR, DEFAULT_QUOTE);
			String[] nextLine;
			int counter = 0;
			while ((nextLine = reader.readNext()) != null) {
				if (counter > 0) 
					characterService.addCharReader(new NarutoCharacter(nextLine[0], Integer.valueOf(nextLine[1])));
				counter++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readSkills(File file) {
		try {
			final BufferedReader in = new BufferedReader(new FileReader(file));
			CSVReader reader = new CSVReader(in, DEFAULT_SEPERATOR, DEFAULT_QUOTE);
			String[] nextLine;
			int counter = 0;
			while ((nextLine = reader.readNext()) != null) {
				if (counter > 0) {
					NarutoCharacter narutoCharacter = characterService.findCharacterWithName(nextLine[0]);
					Skill skill = new Skill();
					skill.setCharacter(narutoCharacter);
					skill.setName(nextLine[1]);
					skill.setBeschreibung(nextLine[2]);
					skill.setSkillTypeEnum(SkillTypeEnum.getEnumWithName(nextLine[3]));
					skill.setAnzAusloesen(Integer.valueOf(nextLine[4]));
					skill.setEffektEnumVerfolgen(characterService.createEffektSet(EffektEnum.getEnumWithName(nextLine[5]),
							EffektEnum.getEnumWithName(nextLine[6]), EffektEnum.getEnumWithName(nextLine[7])));
					skill.setEffektEnumAusloesen(characterService.createEffektSet(EffektEnum.getEnumWithName(nextLine[8]),
							EffektEnum.getEnumWithName(nextLine[9]), EffektEnum.getEnumWithName(nextLine[10])));
					Set<Skill> skills = narutoCharacter.getSkill();
					if(skills == null || skills.size() == 0)
						skills = new HashSet<>();
					skill.seteSTD(Boolean.parseBoolean(nextLine[11]));
					skills.add(skill);
					narutoCharacter.setSkill(skills);
					characterService.addCharReader(narutoCharacter);
					characterService.addSkillReader(skill);
				}
				counter++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
