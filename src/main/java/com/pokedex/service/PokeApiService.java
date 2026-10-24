package com.pokedex.service;

import com.pokedex.api.PokeApiClient;
import com.pokedex.model.Pokemon;
import com.pokedex.model.PokemonComparisonResult;
import com.pokedex.model.Stat;
import org.springframework.stereotype.Service;

import java.util.List;

// Service métier qui centralise les appels à PokéAPI et la logique de comparaison de puissance d'attaque.
@Service
public class PokeApiService {
    private final PokeApiClient pokeApiClient;

    public PokeApiService(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public Pokemon getPokemon(String input) {
        return pokeApiClient.getPokemon(input);
    }

    public List<Pokemon> getPokemonList(int limit, int offset) {
        return pokeApiClient.getPokemonList(limit, offset);
    }

    public PokemonComparisonResult compareAttack(String firstPokemonName, String secondPokemonName) {
        Pokemon firstPokemon = getPokemon(firstPokemonName);
        Pokemon secondPokemon = getPokemon(secondPokemonName);

        int firstAttack = getAttackValue(firstPokemon);
        int secondAttack = getAttackValue(secondPokemon);

        return new PokemonComparisonResult(firstPokemon.getName(), secondPokemon.getName(), firstAttack, secondAttack);
    }

    public int getAttackValue(String pokemonName) {
        Pokemon pokemon = getPokemon(pokemonName);
        return getAttackValue(pokemon);
    }

    public int getAttackDifference(String firstPokemonName, String secondPokemonName) {
        PokemonComparisonResult result = compareAttack(firstPokemonName, secondPokemonName);
        return result.getDifference();
    }

    private int getAttackValue(Pokemon pokemon) {
        if (pokemon == null || pokemon.getStats() == null) {
            return 0;
        }

        return pokemon.getStats().stream()
                .filter(stat -> "Attack".equalsIgnoreCase(stat.getName()))
                .mapToInt(Stat::getValue)
                .findFirst()
                .orElse(0);
    }
}
