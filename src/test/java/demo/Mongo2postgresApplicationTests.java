package demo;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

import demo.Hero.UNIVERSE;


@RunWith( SpringRunner.class )
@SpringBootTest
@ContextConfiguration( initializers = { Mongo2postgresApplicationTests.Initializer.class } )
public class Mongo2postgresApplicationTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer( "postgres:10.4" )
            .withDatabaseName( "sampledb" ).withUsername( "sampleuser" ).withPassword( "samplepwd" )
            .withStartupTimeout( Duration.ofSeconds( 600 ) );

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize( ConfigurableApplicationContext configurableApplicationContext ) {
            System.out.println( "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl() + "\nspring.datasource.username="
                                + postgreSQLContainer.getUsername() + "\nspring.datasource.password=" + postgreSQLContainer.getPassword() );

            TestPropertyValues
                    .of( "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                         "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                         "spring.datasource.password=" + postgreSQLContainer.getPassword() )
                    .applyTo( configurableApplicationContext.getEnvironment() );
        }
    }

    @Autowired
    HerosRepository repo;

    @Test
    public void contextLoads() {

        var superman = new Hero();
        superman.setName( "Superman" );
        superman.setAlterEgo( new AlterEgo( "Clark Kent", "Jounalist" ) );
        superman.setUniverse( UNIVERSE.DC );
        superman.setSuperpowers( Set
                .of( "X-Ray Vision", "Super-Breath", "Heat Vision", "Super-Speed", "Flight", "Super-Strength", "Invulnerability" ) );
        repo.saveAndFlush( new HeroEntity( superman ) );

        var batman = new Hero();
        batman.setName( "Batman" );
        batman.setAlterEgo( new AlterEgo( "Bruce Wayne", "Billionaire" ) );
        batman.setUniverse( UNIVERSE.DC );
        batman.setSuperpowers( Set.of( "X-Ray Vision", "Genius Intellect", "Invisibility", "Flight", "Will-Power", "Reflexes" ) );

        var ironman = new Hero();
        ironman.setName( "Iron Man" );
        ironman.setAlterEgo( new AlterEgo( "Toni Stark", "Genious" ) );
        ironman.setUniverse( UNIVERSE.MARVEL );
        ironman.setSuperpowers( Set.of( "Direct Cybernetic Interface",
                                        "Wireless Communication",
                                        "Reflexes",
                                        "Regenerative Systems",
                                        "Minor Shapeshifting",
                                        "Billionaire",
                                        "Playboy" ) );

        repo.saveAll( Arrays.asList( new HeroEntity( batman ), new HeroEntity( ironman ) ) );

        List<HeroEntity> findAll = repo.findAll();
        findAll.forEach( System.out::println );

        List<HeroEntity> findAllByName = repo.findAllByName( "Iron Man" );
        findAllByName.forEach( System.out::println );

        List<HeroEntity> findAllBySuperpower = repo.findAllBySuperpower( "Reflexes" );
        findAllBySuperpower.forEach( System.out::println );
    }

}
