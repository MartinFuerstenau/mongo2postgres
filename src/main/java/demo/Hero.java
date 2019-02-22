package demo;


import java.util.Set;

import lombok.Data;


@Data
public class Hero {

    enum UNIVERSE {
        DC,
        MARVEL
    }

    private String name;
    private String superpower;
    private Set<String> superpowers;
    private AlterEgo alterEgo;
    private UNIVERSE universe;
}
