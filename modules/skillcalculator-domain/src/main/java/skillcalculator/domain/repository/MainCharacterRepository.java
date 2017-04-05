package skillcalculator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import skillcalculator.domain.model.MainCharacter;

public interface MainCharacterRepository extends JpaRepository<MainCharacter, Long> {
	
	@Query("SELECT main FROM MainCharacter main WHERE main.name = ?1")
	public MainCharacter findByName(String name);

}
