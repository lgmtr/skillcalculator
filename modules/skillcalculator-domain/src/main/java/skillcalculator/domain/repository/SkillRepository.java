package skillcalculator.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import skillcalculator.domain.model.MainCharacter;
import skillcalculator.domain.model.Skill;
import skillcalculator.domain.model.SkillTypeEnum;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	
	@Query("SELECT skill FROM Skill skill WHERE skill.name = ?1")
	public List<Skill> findByName(String name);
	
	@Query("SELECT skill FROM Skill skill WHERE skill.name = ?1 AND skill.main = ?2")
	public Skill findByNameForMain(String name, MainCharacter main);
	
	@Query("SELECT skill FROM Skill skill WHERE skill.skillTypeEnum = ?1 AND skill.main = ?2")
	public List<Skill> getSkillsFromSpecificMainWithType(SkillTypeEnum type, MainCharacter main);

}
