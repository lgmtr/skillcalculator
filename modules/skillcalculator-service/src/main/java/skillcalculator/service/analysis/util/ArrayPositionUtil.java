package skillcalculator.service.analysis.util;

import skillcalculator.domain.model.NarutoCharacter;

public class ArrayPositionUtil {
	
	public static NarutoCharacter[] addCharacterToSpecificPosition(NarutoCharacter[] array, NarutoCharacter character, int position){
		NarutoCharacter[] newArray = new NarutoCharacter[array.length + 1];
		for (int i = 0; i < newArray.length; i++) {
			if(i<position){
				newArray[i] = array[i];
			}else if(i>position){
				newArray[i] = array[i-1];
			}else
				newArray[i] = character;
		}
		return newArray;
	}

}
