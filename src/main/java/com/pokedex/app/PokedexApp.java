package com.pokedex.app;

import com.pokedex.api.PokeApiClient;
import com.pokedex.model.Pokemon;
import com.pokedex.model.Stat;

import java.util.List;
import java.util.Scanner;

public class PokedexApp {
    public static void main(String[] args) {
        // Lancement du programme et création des outils nécessaires.
        System.out.println("=====================================");
        System.out.println("Bienvenue dans votre Pokédex Java !");
        System.out.println("=====================================");

        PokeApiClient client = new PokeApiClient();
        Scanner scanner = new Scanner(System.in);

        // Boucle principale : on demande un Pokémon jusqu'à ce que l'utilisateur quitte.
        while (true) {
            System.out.println("\nEntrez le nom ou le numéro d'un Pokémon (ou 'quitter' pour sortir) :");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quitter")) {
                System.out.println("À bientôt !");
                break;
            }

            try {
                // Ici, l'application appelle l'API pour obtenir les données du Pokémon choisi.
                Pokemon pokemon = client.getPokemon(input);
                displayPokemon(pokemon);
                compareWithStarter(pokemon);
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Impossible de récupérer les informations du Pokémon. Vérifiez votre connexion Internet.");
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    private static void displayPokemon(Pokemon pokemon) {
        System.out.println("\n=== FICHE POKÉMON ===");
        System.out.println("Nom : " + pokemon.getName());
        System.out.println("N° : " + pokemon.getId());
        System.out.println("Taille : " + pokemon.getHeight() / 10.0 + " m");
        System.out.println("Poids : " + pokemon.getWeight() / 10.0 + " kg");

        System.out.print("Types : ");
        System.out.println(String.join(" / ", pokemon.getTypes()));

        System.out.print("Capacités : ");
        System.out.println(String.join(", ", pokemon.getAbilities()));

        System.out.println("\nStatistiques :");
        for (Stat stat : pokemon.getStats()) {
            System.out.println("- " + stat.getName() + " : " + stat.getValue());
        }

        System.out.println("\nImage : " + pokemon.getImageUrl());
    }

    private static void compareWithStarter(Pokemon pokemon) {
        List<Stat> stats = pokemon.getStats();
        int attack = 0;
        for (Stat stat : stats) {
            if (stat.getName().equalsIgnoreCase("Attack")) {
                attack = stat.getValue();
                break;
            }
        }

        if (attack > 0) {
            String comparison = attack >= 55 ? "Cet Pokémon a une attaque assez forte pour être un bon combattant !" : "Il reste plutôt équilibré, mais pas le plus puissant en attaque.";
            System.out.println("\nBonus : " + comparison);
        }
    }
}
