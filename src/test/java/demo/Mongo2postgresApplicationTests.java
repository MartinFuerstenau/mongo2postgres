package demo;

import java.time.Duration;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = { Mongo2postgresApplicationTests.Initializer.class })
public class Mongo2postgresApplicationTests {

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(
			"postgres:10.4").withDatabaseName("sampledb").withUsername("sampleuser").withPassword("samplepwd")
					.withStartupTimeout(Duration.ofSeconds(600));

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			System.out.println("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl()
					+ "\nspring.datasource.username=" + postgreSQLContainer.getUsername()
					+ "\nspring.datasource.password=" + postgreSQLContainer.getPassword());

			TestPropertyValues
					.of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
							"spring.datasource.username=" + postgreSQLContainer.getUsername(),
							"spring.datasource.password=" + postgreSQLContainer.getPassword())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Autowired
	HerosRepository repo;

	@Test
	public void contextLoads() {
		for (int i = 0; i < 10; i++) {
			Hero hero = new Hero();
			hero.setName("Super Hero " + i);
			hero.setSuperpower("SuperPower" + i);

			HeroEntity heroEntity = new HeroEntity();
			heroEntity.setHero(hero);

			System.out.println(heroEntity);
			HeroEntity save = repo.save(heroEntity);
			System.out.println(save);
		}

		List<HeroEntity> findAll = repo.findAll();
		findAll.forEach(System.out::println);
	}

}
