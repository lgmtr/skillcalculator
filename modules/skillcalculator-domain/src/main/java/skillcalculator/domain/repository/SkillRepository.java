package skillcalculator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import skillcalculator.domain.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
