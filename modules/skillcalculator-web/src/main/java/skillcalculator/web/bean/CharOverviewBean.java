package skillcalculator.web.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.service.character.CharacterService;
import skillcalculator.service.export.ReaderFileService;
import skillcalculator.service.export.WriteFileService;

@Component("charOverviewBean")
@Scope("session")
@ManagedBean
public class CharOverviewBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private CharacterService characterService;
	
	@Resource
	private WriteFileService writeFileService;

	@Resource
	private ReaderFileService readerFileService;
	
	private List<NarutoCharacter> narutoCharList;
	
	public List<NarutoCharacter> getNarutoCharList() {
		readFileTest();
		return characterService.getAllCharacters();
	}

	public void readFileTest() {
		String fileNameChar = this.getClass().getResource("/testdata/Export_Test_Char.csv").getFile();
		File fileChar = new File(fileNameChar);
		readerFileService.readCharacter(fileChar);
		String fileNameSkill = this.getClass().getResource("/testdata/Export_Test_Skill.csv").getFile();
		File fileSkill = new File(fileNameSkill);
		readerFileService.readSkills(fileSkill);
	}
	
	public void onRowToggle(ToggleEvent event) {
	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
	                                        "Row State " + event.getVisibility(),
	                                        "Model:" + ((NarutoCharacter) event.getData()).getName());

	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
