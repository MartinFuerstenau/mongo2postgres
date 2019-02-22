package demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HerosRepository extends JpaRepository<HeroEntity, Long> {

	@Query(value = "SELECT * FROM heros WHERE hero ->> 'name' = ?1", nativeQuery = true)
	HeroEntity findAllByName(String name);

	@Query(value = "SELECT * FROM heros WHERE hero -> 'superpowers' @> to_jsonb(:superpower)", nativeQuery = true)
	List<HeroEntity> findAllBySuperpower(@Param("superpower") String superpower);
}
