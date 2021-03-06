package skillcalculator.domain.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "character")
public class NarutoCharacter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	private String id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "character", cascade=CascadeType.REMOVE)
	private Set<Skill> skill = new HashSet<Skill>(0);
	
	@Column(name = "stars", nullable = false)
	private int stars;
	
	private boolean isMain;
	
	public NarutoCharacter(String name, int stars, Set<Skill> skills) {
		this.id = name + "_" + stars;
		this.name = name;
		this.stars = stars;
		this.skill = skills;
	}
	
	public NarutoCharacter(String name, int stars) {
		this(name, stars, new HashSet<Skill>(0));
	}

	public NarutoCharacter() {
		this("", 0, new HashSet<Skill>(0));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Skill> getSkill() {
		return skill;
	}

	public void setSkill(Set<Skill> skill) {
		this.skill = skill;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

}
