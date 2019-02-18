package demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity(name = "Heros")
@Table(name = "HEROS")
@Data
public class HeroEntity extends BaseEntity {

	@Id
	private Long id;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private Hero hero;
}
