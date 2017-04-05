package skillcalculator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import skillcalculator.domain.model.PetModel;

public interface PetRepository extends JpaRepository<PetModel, Long> {

	@Query("SELECT pet FROM PetModel pet WHERE pet.name = ?1")
	public PetModel findByName(String name);
}
