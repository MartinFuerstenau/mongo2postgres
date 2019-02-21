package demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HerosRepository extends JpaRepository<HeroEntity, Long> {

}
