package skillcalculator.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.service.character.CharacterService;

@Component("charOverviewBean")
@Scope("session")
@ManagedBean
public class CharOverviewBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private CharacterService characterService;
	
	private List<NarutoCharacter> narutoCharList;
	
	public List<NarutoCharacter> getNarutoCharList() {
		return characterService.getAllCharacters();
	}

}
