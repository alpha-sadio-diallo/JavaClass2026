# Pokédex Java

Application Java console qui interroge l'API publique PokéAPI pour rechercher un Pokémon par nom ou numéro.

## Fonctionnalités

- recherche d'un Pokémon
- affichage du nom, numéro, taille, poids, types, capacités et statistiques
- affichage de l'image officielle
- bonus de comparaison rapide sur la statistique d'attaque

## Prérequis

- JDK 17+
- accès internet

## Lancer le projet

### Avec IntelliJ / Eclipse

1. Ouvrir le dossier du projet.
2. Importer comme projet Maven (ou simplement ouvrir la racine).
3. Lancer la classe `com.pokedex.app.PokedexApp`.

### En ligne de commande

```bash
javac -cp gson.jar -d out $(find src/main/java -name "*.java")
java -cp out;gson.jar com.pokedex.app.PokedexApp
```

Si vous avez Maven installé :

```bash
mvn clean compile exec:java -Dexec.mainClass=com.pokedex.app.PokedexApp
```
