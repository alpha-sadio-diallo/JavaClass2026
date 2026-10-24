package com.pokedex.controller;

import com.pokedex.model.Pokemon;
import com.pokedex.service.PokeApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PokedexController {
    private final PokeApiService pokeApiService;
    private static final int DEFAULT_VISIBLE_POKEMON = 12;

    public PokedexController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String search,
                       @RequestParam(required = false) String compare,
                       @RequestParam(defaultValue = "false") boolean showAll,
                       Model model) {
        List<Pokemon> allPokemon = pokeApiService.getPokemonList(50, 0);
        model.addAttribute("pokemonList", allPokemon);

        int displayedCount = showAll ? allPokemon.size() : Math.min(DEFAULT_VISIBLE_POKEMON, allPokemon.size());
        model.addAttribute("displayedPokemon", allPokemon.subList(0, displayedCount));
        model.addAttribute("showAll", showAll);
        model.addAttribute("hasMorePokemon", allPokemon.size() > displayedCount);

        if (search != null && !search.isBlank()) {
            try {
                Pokemon pokemon = pokeApiService.getPokemon(search);
                model.addAttribute("pokemon", pokemon);

                if (compare != null && !compare.isBlank()) {
                    Pokemon comparisonPokemon = pokeApiService.getPokemon(compare);
                    int attackDifference = pokeApiService.getAttackDifference(search, compare);
                    model.addAttribute("comparisonPokemon", comparisonPokemon);
                    model.addAttribute("attackDifference", attackDifference);
                    model.addAttribute("searchedAttack", pokeApiService.getAttackValue(search));
                    model.addAttribute("comparedAttack", pokeApiService.getAttackValue(compare));
                }
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }
        }

        return "index";
    }
}
