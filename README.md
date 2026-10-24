## Auteurs

Alpha Sadio Diallo
Mamadou Hachimiou Diallo

# Pokédex Java Spring Boot

Application web Java avec Spring Boot qui interroge l'API publique PokéAPI pour rechercher un Pokémon, afficher ses informations et comparer deux Pokémon selon leur puissance d'attaque.

## Fonctionnalités

- recherche d'un Pokémon par nom ou numéro
- affichage du nom, numéro, image, taille, poids, types et capacités
- affichage des statistiques complètes du Pokémon
- liste des 50 premiers Pokémon avec pagination progressive via "Voir plus"
- comparaison de deux Pokémon côte à côte
- calcul de la différence d'attaque et détermination du gagnant

## Stack technique

- Java 17+
- Spring Boot 3
- Thymeleaf
- PokéAPI
- Maven

## Prérequis

- JDK 17+
- Maven
- accès internet

## Lancer le projet

### Avec IntelliJ / Eclipse

1. Ouvrir le dossier du projet.
2. Importer le projet comme projet Maven.
3. Lancer la classe `com.pokedex.PokedexSpringBootApplication`.
4. Ouvrir l'URL : `http://localhost:8081`

### En ligne de commande

```bash
mvn spring-boot:run
```

## Utilisation

- Tapez un nom ou un numéro dans le champ de recherche.
- Sélectionnez un Pokémon dans le menu pour le comparer à celui recherché.
- Utilisez le bouton "Voir plus" pour afficher la suite des 50 premiers Pokémon.

## Remarque

Le projet a été pensé comme une petite application web de Pokédex plus orientée comparaison et expérience utilisateur que la simple API brute.
