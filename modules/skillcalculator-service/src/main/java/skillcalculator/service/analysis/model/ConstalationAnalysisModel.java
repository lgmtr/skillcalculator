package skillcalculator.service.analysis.model;


public class ConstalationAnalysisModel implements Comparable<ConstalationAnalysisModel> {

	private CharacterAnalysisModel firstCharacter;

	private CharacterAnalysisModel secondCharacter;

	private CharacterAnalysisModel thirdCharacter;

	private CharacterAnalysisModel fourthCharacter;

	private int comboValue;

	public ConstalationAnalysisModel(CharacterAnalysisModel firstCharacter, CharacterAnalysisModel secondCharacter, CharacterAnalysisModel thirdCharacter,
			CharacterAnalysisModel fourthCharacter, int comboValue) {
		this.firstCharacter = firstCharacter;
		this.secondCharacter = secondCharacter;
		this.thirdCharacter = thirdCharacter;
		this.fourthCharacter = fourthCharacter;
		this.comboValue = comboValue;
	}

	@Override
	public String toString() {
		return "Best Constalation for Combos is: " + firstCharacter.getCharacter().getName() + " | " + secondCharacter.getCharacter().getName() + " | "
				+ thirdCharacter.getCharacter().getName() + " | " + fourthCharacter.getCharacter().getName() + "\nComboValue is: " + comboValue;
	}

	public CharacterAnalysisModel getFirstCharacter() {
		return firstCharacter;
	}

	public void setFirstCharacter(CharacterAnalysisModel firstCharacter) {
		this.firstCharacter = firstCharacter;
	}

	public CharacterAnalysisModel getSecondCharacter() {
		return secondCharacter;
	}

	public void setSecondCharacter(CharacterAnalysisModel secondCharacter) {
		this.secondCharacter = secondCharacter;
	}

	public CharacterAnalysisModel getThirdCharacter() {
		return thirdCharacter;
	}

	public void setThirdCharacter(CharacterAnalysisModel thirdCharacter) {
		this.thirdCharacter = thirdCharacter;
	}

	public CharacterAnalysisModel getFourthCharacter() {
		return fourthCharacter;
	}

	public void setFourthCharacter(CharacterAnalysisModel fourthCharacter) {
		this.fourthCharacter = fourthCharacter;
	}

	public int getComboValue() {
		return comboValue;
	}

	public void setComboValue(int comboValue) {
		this.comboValue = comboValue;
	}

	@Override
	public int compareTo(ConstalationAnalysisModel o) {
		if (comboValue < o.comboValue)
			return -1;
		else if (comboValue > o.comboValue)
			return 1;
		return 0;
	}

}
