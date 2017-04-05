package skillcalculator.service.analysis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import skillcalculator.domain.model.EffektEnum;
import skillcalculator.domain.model.MainCharacter;
import skillcalculator.domain.model.NarutoCharacter;
import skillcalculator.domain.model.PetModel;

@Service
public class CalculateCombinationsWithMain {

	@Resource
	private TaskExecutor taskExecutor;
	
	@Resource
	private ApplicationContext context;

	public void calculateConstalation(List<NarutoCharacter[]> constilationList, List<PetModel> pets, MainCharacter main, int minComboValue,
			EffektEnum... priorityEffects) {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) taskExecutor;
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		for (NarutoCharacter[] constalation : constilationList) {
			taskExecutor.execute(new ConstalationCalucator(constalation, pets, main, minComboValue, priorityEffects, context));
		}

	}
}
