package demo;

import java.time.Duration;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Test
	public void contextLoads() {
		System.out.println("TEST");
	}

}
