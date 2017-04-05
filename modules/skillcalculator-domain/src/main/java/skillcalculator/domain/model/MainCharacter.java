package skillcalculator.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "main")
public class MainCharacter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MAIN_ID", unique = true, nullable = false)
	private long id;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "main", cascade=CascadeType.REMOVE)
	private Set<Skill> opakSkill = new HashSet<Skill>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "main", cascade=CascadeType.REMOVE)
	private Set<Skill> standardSkill = new HashSet<Skill>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "main", cascade=CascadeType.REMOVE)
	private Set<Skill> passivSkillOne = new HashSet<Skill>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "main", cascade=CascadeType.REMOVE)
	private Set<Skill> passivSkillTwo = new HashSet<Skill>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "main", cascade=CascadeType.REMOVE)
	private Set<Skill> passivSkillThree = new HashSet<Skill>(0);
	
	public MainCharacter() {
	}
	
 	public MainCharacter(String name, Set<Skill> opakSkill, Set<Skill> standardSkill, Set<Skill> passivSkillOne, Set<Skill> passivSkillTwo, Set<Skill> passivSkillThree) {
		this.name = name;
		this.opakSkill = opakSkill;
		this.standardSkill = standardSkill;
		this.passivSkillOne = passivSkillOne;
		this.passivSkillTwo = passivSkillTwo;
		this.passivSkillThree = passivSkillThree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Skill> getOpakSkill() {
		return opakSkill;
	}
	
	public List<Skill> getOpakSkillAsList() {
		return new ArrayList<>(opakSkill);
	}

	public void setOpakSkill(Set<Skill> opakSkill) {
		this.opakSkill = opakSkill;
	}

	public Set<Skill> getStandardSkill() {
		return standardSkill;
	}
	
	public List<Skill> getStandardAsList() {
		return new ArrayList<>(standardSkill);
	}

	public void setStandardSkill(Set<Skill> standardSkill) {
		this.standardSkill = standardSkill;
	}

	public Set<Skill> getPassivSkillOne() {
		return passivSkillOne;
	}
	
	public List<Skill> getPassivSkillOneAsList() {
		return new ArrayList<>(passivSkillOne);
	}

	public void setPassivSkillOne(Set<Skill> passivSkillOne) {
		this.passivSkillOne = passivSkillOne;
	}

	public Set<Skill> getPassivSkillTwo() {
		return passivSkillTwo;
	}

	public void setPassivSkillTwo(Set<Skill> passivSkillTwo) {
		this.passivSkillTwo = passivSkillTwo;
	}

	public Set<Skill> getPassivSkillThree() {
		return passivSkillThree;
	}

	public void setPassivSkillThree(Set<Skill> passivSkillThree) {
		this.passivSkillThree = passivSkillThree;
	}

}
