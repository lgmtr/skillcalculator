package skillcalculator.service.analysis.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import skillcalculator.service.analysis.model.ConstalationAnalysisModel;

@Service
public class BestConstilationListHolder {

	private List<ConstalationAnalysisModel> bestConstalationList;

	public void clearList() {
		if(bestConstalationList == null)
			bestConstalationList = new ArrayList<>();
		bestConstalationList.clear();
	}

	public void addToList(ConstalationAnalysisModel cam) {
		bestConstalationList.add(cam);
	}

	public List<ConstalationAnalysisModel> getList() {
		return bestConstalationList;
	}

}
