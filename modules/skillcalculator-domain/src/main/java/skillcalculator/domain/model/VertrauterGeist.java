package skillcalculator.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vertrauter_geist")
public class VertrauterGeist implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", unique = true, nullable = false, length = 45)
	private String name;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name = "SKILL_ID")
	private Skill skill;

}
