package com.java.vinci.demo.service;

import com.java.vinci.demo.model.ApiResponseModel;
import com.java.vinci.demo.model.Pokemon;
import com.java.vinci.demo.model.Statistiques;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PokemonServiceImpl implements IPokemonService {
    private final String apiUrl = "https://pokeapi.co/api/v2/pokemon/";
    private final RestClient restClient = RestClient.create();
    private final List<Pokemon> fetchedPokemons = new ArrayList<>();
    @Override
    public List<Pokemon> getAllPokemons() {
        ApiResponseModel response = restClient.get()
                .uri(apiUrl)
                .retrieve()
                .body(ApiResponseModel.class);

        if (response == null || response.getResults() == null) {
            throw new IllegalStateException("PokeAPI returned an empty Pokémon list");
        }

        
        for (Map<String, String> pokemonData : response.getResults()) {
            Map<String, Object> details = restClient.get()
                    .uri(pokemonData.get("url"))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (details == null) {
                throw new IllegalStateException("Details vides pour " + pokemonData.get("name"));
            }

            Pokemon pokemon = new Pokemon();
            pokemon.setId(((Number) details.get("id")).longValue());
            pokemon.setName((String) details.get("name"));
            pokemon.setTaille(String.valueOf(details.get("height")));
            pokemon.setPoids(String.valueOf(details.get("weight")));
            pokemon.setType(extractTypeNames(details.get("types")));
            pokemon.setStats(extractStats(details.get("stats")));
            pokemon.setImageUrl(extractImageUrl(details.get("sprites")));
            pokemon.setImageUrl(extractImageUrl(details.get("sprites")));
            pokemon.setCapacites(extractCapacities(details.get("abilities")));
            fetchedPokemons.add(pokemon);
        }


        // classer les pokemons par leur puissance d'attaque (attack) en ordre décroissant
        fetchedPokemons.sort((p1, p2) -> Integer.compare(p2.getStats().getAttack(), p1.getStats().getAttack()));
        return fetchedPokemons;
    }

    // ici j'extrait les types
    private String[] extractTypeNames(Object typesValue) {
        if (!(typesValue instanceof List<?> types)) {
            throw new IllegalStateException("aucune list de types trouvee");
        }

        List<String> names = new ArrayList<>();
        for (Object typeValue : types) {
            if (!(typeValue instanceof Map<?, ?> typeEntry)
                    || !(typeEntry.get("type") instanceof Map<?, ?> type)
                    || !(type.get("name") instanceof String name)) {
                throw new IllegalStateException("PokeAPI returned an invalid Pokémon type entry");
            }
            names.add(name);
        }
        return names.toArray(String[]::new);
    }

    private String extractImageUrl(Object spritesValue) {
        if (spritesValue instanceof Map<?, ?> sprites
                && sprites.get("other") instanceof Map<?, ?> other
                && other.get("home") instanceof Map<?, ?> home
                && home.get("front_default") instanceof String url) {
            return url;
        }
        return null;
    }

    private String[] extractCapacities(Object capacitiesValue) {
        if (!(capacitiesValue instanceof List<?> capacities)) {
            throw new IllegalStateException(
                    "PokeAPI returned an invalid Pokémon abilities value");
        }

        List<String> names = new ArrayList<>();

        for (Object capacityValue : capacities) {
            if (capacityValue instanceof Map<?, ?> capacity
                    && capacity.get("ability") instanceof Map<?, ?> ability
                    && ability.get("name") instanceof String name) {
                names.add(name);
            }
        }

        return names.toArray(String[]::new);
    }



    private Statistiques extractStats(Object statsValue) {
        if (!(statsValue instanceof List<?> stats)) {
            throw new IllegalStateException("aucune list de stats trouvee");
        }

        return new Statistiques(
                findStat(stats, "hp"),
                findStat(stats, "attack"),
                findStat(stats, "defense"),
                findStat(stats, "special-attack"),
                findStat(stats, "special-defense"),
                findStat(stats, "speed")
        );
    }

    private int findStat(List<?> stats, String name) {
        for (Object statValue : stats) {
            if (statValue instanceof Map<?, ?> statEntry
                    && statEntry.get("stat") instanceof Map<?, ?> stat
                    && name.equals(stat.get("name"))
                    && statEntry.get("base_stat") instanceof Number value) {
                return value.intValue();
            }
        }
        throw new IllegalStateException("aucun stat trouvee: " + name);
    }

    @Override
    public Pokemon getPokemonByName(String name) {
        String lowerCaseName = name.toLowerCase();
        for (Pokemon pokemon : fetchedPokemons) {
            if (pokemon.getName().toLowerCase().contains(lowerCaseName)) {
                return pokemon;
            }
        }
        return null;
    }
}
