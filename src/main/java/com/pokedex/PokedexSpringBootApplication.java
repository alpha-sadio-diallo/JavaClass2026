package com.pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Point d'entrée principal de l'application Spring Boot : lance le serveur web et initialise le Pokédex.
@SpringBootApplication
public class PokedexSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokedexSpringBootApplication.class, args);
    }
}
