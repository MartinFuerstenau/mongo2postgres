package demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "heros", path = "heros")
public interface HerosRepository extends JpaRepository<HeroEntity, Long> {

	@Query(value = "SELECT * FROM heros WHERE hero ->> 'name' = ?1", nativeQuery = true)
	HeroEntity findByName(String name);

	@Query(value = "SELECT * FROM heros WHERE hero -> 'superpowers' @> to_jsonb(:superpower)", nativeQuery = true)
	List<HeroEntity> findAllBySuperpower(@Param("superpower") String superpower);
}
