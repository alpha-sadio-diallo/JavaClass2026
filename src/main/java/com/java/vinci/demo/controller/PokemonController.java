package com.java.vinci.demo.controller;

import com.java.vinci.demo.model.Pokemon;
import com.java.vinci.demo.service.IPokemonService;
import com.java.vinci.demo.service.PokemonServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {
    
    private final PokemonServiceImpl pokemonService;

    public PokemonController(PokemonServiceImpl pokemonService) {
        this.pokemonService = pokemonService;
    }


    @GetMapping("/")
    public List<Pokemon> getAllPokemons() {
        return pokemonService.getAllPokemons();
    }

    @GetMapping("/{name}")
    public Pokemon getPokemonByName(@PathVariable String name) {
        return pokemonService.getPokemonByName(name);
    }
}