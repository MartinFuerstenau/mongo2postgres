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
import lombok.NoArgsConstructor;


@Entity( name = "Heros" )
@Table( name = "HEROS" )
@Data
@NoArgsConstructor
public class HeroEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "my_heros_sequence" )
    @SequenceGenerator( name = "my_heros_sequence", sequenceName = "heros_sequence" )
    private Long id;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb" )
    private Hero hero;

    public HeroEntity( Hero hero ) {
        super();
        this.hero = hero;
    }

}
