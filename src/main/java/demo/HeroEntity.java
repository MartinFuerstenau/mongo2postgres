package demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity(name = "Heros")
@Table(name = "HEROS")
@Data
public class HeroEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_heros_sequence")
	@SequenceGenerator(name = "my_heros_sequence", sequenceName = "heros_sequence")
	private Long id;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private Hero hero;
}
