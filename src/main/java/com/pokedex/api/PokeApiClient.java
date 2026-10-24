package com.pokedex.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.model.Pokemon;
import com.pokedex.model.Stat;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokeApiClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Pokemon getPokemon(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Veuillez entrer un nom ou un numéro de Pokémon.");
        }

        String url = BASE_URL + "/" + input.trim().toLowerCase();
        String response = restTemplate.getForObject(url, String.class);

        if (response == null || response.isBlank()) {
            throw new IllegalStateException("Réponse vide reçue de l'API Pokémon.");
        }

        try {
            JsonNode root = objectMapper.readTree(response);
            return mapPokemon(root);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors du traitement des données du Pokémon.", e);
        }
    }

    public List<Pokemon> getPokemonList(int limit, int offset) {
        String url = BASE_URL + "?limit=" + limit + "&offset=" + offset;
        String response = restTemplate.getForObject(url, String.class);

        if (response == null || response.isBlank()) {
            throw new IllegalStateException("Réponse vide reçue pour la liste des Pokémon.");
        }

        try {
            JsonNode root = objectMapper.readTree(response);
            List<Pokemon> pokemonList = new ArrayList<>();
            for (JsonNode result : root.path("results")) {
                String name = result.path("name").asText();
                String detailUrl = result.path("url").asText();
                Pokemon pokemon = new Pokemon();
                pokemon.setName(capitalize(name));
                pokemon.setId(parsePokemonId(detailUrl));
                pokemonList.add(pokemon);
            }
            return pokemonList;
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors du chargement de la liste des Pokémon.", e);
        }
    }

    private Pokemon mapPokemon(JsonNode root) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(root.path("id").asInt());
        pokemon.setName(capitalize(root.path("name").asText()));
        pokemon.setHeight(root.path("height").asInt());
        pokemon.setWeight(root.path("weight").asInt());

        JsonNode officialArtwork = root.path("sprites").path("other").path("official-artwork");
        String imageUrl = officialArtwork.path("front_default").asText();
        if (imageUrl == null || imageUrl.isBlank()) {
            imageUrl = root.path("sprites").path("front_default").asText();
        }
        pokemon.setImageUrl(imageUrl);

        List<String> types = new ArrayList<>();
        for (JsonNode typeNode : root.path("types")) {
            types.add(capitalize(typeNode.path("type").path("name").asText()));
        }
        pokemon.setTypes(types);

        List<String> abilities = new ArrayList<>();
        for (JsonNode abilityNode : root.path("abilities")) {
            abilities.add(capitalize(abilityNode.path("ability").path("name").asText()));
        }
        pokemon.setAbilities(abilities);

        List<Stat> stats = new ArrayList<>();
        for (JsonNode statNode : root.path("stats")) {
            String statName = capitalize(statNode.path("stat").path("name").asText());
            int statValue = statNode.path("base_stat").asInt();
            stats.add(new Stat(statName, statValue));
        }
        pokemon.setStats(stats);

        return pokemon;
    }

    private int parsePokemonId(String detailUrl) {
        if (detailUrl == null || detailUrl.isBlank()) {
            return 0;
        }
        String[] segments = detailUrl.split("/");
        String idText = segments[segments.length - 1];
        if (idText == null || idText.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String capitalize(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }

        String[] parts = text.split("-");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            if (part.isEmpty()) {
                continue;
            }
            result.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1));
        }
        return result.toString();
    }
}
