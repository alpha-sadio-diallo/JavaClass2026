package com.pokedex.service;

import com.pokedex.api.PokeApiClient;
import com.pokedex.model.Pokemon;
import com.pokedex.model.Stat;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public int getAttackDifference(String firstPokemonName, String secondPokemonName) {
        int firstAttack = getAttackValue(firstPokemonName);
        int secondAttack = getAttackValue(secondPokemonName);

        return Math.abs(firstAttack - secondAttack);
    }

    public int getAttackValue(String pokemonName) {
        Pokemon pokemon = getPokemon(pokemonName);
        return getAttackValue(pokemon);
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
