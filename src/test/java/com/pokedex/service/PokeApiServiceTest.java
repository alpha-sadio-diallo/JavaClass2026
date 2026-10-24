package com.pokedex.service;

import com.pokedex.api.PokeApiClient;
import com.pokedex.model.Pokemon;
import com.pokedex.model.Stat;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokeApiServiceTest {

    @Test
    void getPokemonListShouldReturnRequestedPokemonBatch() {
        Map<String, Pokemon> pokemons = new LinkedHashMap<>();
        pokemons.put("bulbasaur", pokemon(1, "Bulbasaur"));
        pokemons.put("ivysaur", pokemon(2, "Ivysaur"));

        PokeApiClient pokeApiClient = new FakePokeApiClient(pokemons);

        PokeApiService service = new PokeApiService(pokeApiClient);

        List<Pokemon> result = service.getPokemonList(50, 0);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Bulbasaur", result.get(0).getName());
    }

    @Test
    void getAttackDifferenceShouldCompareAttackStats() {
        Map<String, Pokemon> pokemons = new LinkedHashMap<>();
        pokemons.put("pikachu", pokemonWithAttack("Pikachu", 55));
        pokemons.put("charizard", pokemonWithAttack("Charizard", 90));

        PokeApiClient pokeApiClient = new FakePokeApiClient(pokemons);

        PokeApiService service = new PokeApiService(pokeApiClient);

        int difference = service.getAttackDifference("pikachu", "charizard");

        assertEquals(35, difference);
    }

    private static Pokemon pokemon(int id, String name) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id);
        pokemon.setName(name);
        return pokemon;
    }

    private static Pokemon pokemonWithAttack(String name, int attackValue) {
        Pokemon pokemon = pokemon(1, name);
        pokemon.setStats(List.of(
                new Stat("Attack", attackValue),
                new Stat("Defense", 30)
        ));
        return pokemon;
    }

    private static class FakePokeApiClient extends PokeApiClient {
        private final Map<String, Pokemon> pokemons;

        private FakePokeApiClient(Map<String, Pokemon> pokemons) {
            this.pokemons = pokemons;
        }

        @Override
        public Pokemon getPokemon(String input) {
            return pokemons.get(input.trim().toLowerCase());
        }

        @Override
        public List<Pokemon> getPokemonList(int limit, int offset) {
            return List.copyOf(pokemons.values());
        }
    }
}
