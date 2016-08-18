package skillcalculator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import skillcalculator.domain.model.NarutoCharacter;

public interface NarutoCharacterRepository extends JpaRepository<NarutoCharacter, String> {

}
