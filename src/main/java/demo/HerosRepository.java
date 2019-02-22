package demo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HerosRepository extends JpaRepository<HeroEntity, Long> {

    @Query( value = "SELECT * FROM heros WHERE hero ->> 'name' = ?1", nativeQuery = true )
    List<HeroEntity> findAllByName( String name );

    @Query( value = "SELECT * FROM heros WHERE hero ->> 'superpowers' n#> ?1", nativeQuery = true )
    List<HeroEntity> findAllBySuperpower( String superpower );
}
