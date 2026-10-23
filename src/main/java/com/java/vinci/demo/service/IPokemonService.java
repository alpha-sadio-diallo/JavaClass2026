package com.java.vinci.demo.service;

import com.java.vinci.demo.model.Pokemon;
import java.util.List;

public interface IPokemonService {
    List<Pokemon> getAllPokemons();
    Pokemon getPokemonByName(String name);
   
}