package skillcalculator.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "skill")
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SKILL_ID", unique = true, nullable = false)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "character")
	private NarutoCharacter character;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "main")
	private MainCharacter main;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="skill")
	private PetModel pet;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "BESCHREIBUNG", nullable = true)
	private String beschreibung;
	
	@Column(name = "SKILL_TYPE", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private SkillTypeEnum skillTypeEnum;
	
	@ElementCollection(fetch = FetchType.EAGER, targetClass = EffektEnum.class)
	@CollectionTable(name = "EFFEKT_VERFOLGEN", joinColumns = @JoinColumn(name = "SKILL_ID"))
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "VERFOLGEN")
	private Set<EffektEnum> effektEnumVerfolgen = new HashSet<>(0);
	
	@ElementCollection(fetch = FetchType.EAGER, targetClass = EffektEnum.class)
	@CollectionTable(name = "EFFEKT_AUSLOESEN", joinColumns = @JoinColumn(name = "SKILL_ID"))
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "AUSLOESEN")
	private Set<EffektEnum> effektEnumAusloesen = new HashSet<>(0);
	
	@Column(name = "ANZ_AUSLOESEN", nullable = false)
	private int anzAusloesen;
	
	@Column(name = "ERSETZT_STD")
	private boolean eSTD;

	public NarutoCharacter getCharacter() {
		return character;
	}

	public void setCharacter(NarutoCharacter character) {
		this.character = character;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SkillTypeEnum getSkillTypeEnum() {
		return skillTypeEnum;
	}

	public void setSkillTypeEnum(SkillTypeEnum skillTypeEnum) {
		this.skillTypeEnum = skillTypeEnum;
	}

	public Set<EffektEnum> getEffektEnumVerfolgen() {
		return effektEnumVerfolgen;
	}

	public void setEffektEnumVerfolgen(Set<EffektEnum> effektEnumVerfolgen) {
		this.effektEnumVerfolgen = effektEnumVerfolgen;
	}

	public Set<EffektEnum> getEffektEnumAusloesen() {
		return effektEnumAusloesen;
	}

	public void setEffektEnumAusloesen(Set<EffektEnum> effektEnumAusloesen) {
		this.effektEnumAusloesen = effektEnumAusloesen;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public int getAnzAusloesen() {
		return anzAusloesen;
	}

	public void setAnzAusloesen(int anzAusloesen) {
		this.anzAusloesen = anzAusloesen;
	}

	public boolean iseSTD() {
		return eSTD;
	}

	public void seteSTD(boolean eSTD) {
		this.eSTD = eSTD;
	}

	public MainCharacter getMain() {
		return main;
	}

	public void setMain(MainCharacter main) {
		this.main = main;
	}

	public PetModel getPet() {
		return pet;
	}

	public void setPet(PetModel pet) {
		this.pet = pet;
	}

}
