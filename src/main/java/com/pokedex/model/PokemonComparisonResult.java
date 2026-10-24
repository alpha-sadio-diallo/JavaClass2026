package com.pokedex.model;

// Contient le résultat d'un duel entre deux Pokémon, notamment la différence d'attaque et le gagnant.
public class PokemonComparisonResult {
    private final String firstName;
    private final String secondName;
    private final int firstAttack;
    private final int secondAttack;
    private final int difference;
    private final String winner;
    private final String message;

    public PokemonComparisonResult(String firstName, String secondName, int firstAttack, int secondAttack) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstAttack = firstAttack;
        this.secondAttack = secondAttack;
        this.difference = Math.abs(firstAttack - secondAttack);
        this.winner = firstAttack >= secondAttack ? firstName : secondName;
        this.message = firstAttack == secondAttack
                ? "Les deux Pokémon ont la même puissance d'attaque."
                : "Le gagnant est " + winner + " avec une différence de " + difference + " points d'attaque.";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getFirstAttack() {
        return firstAttack;
    }

    public int getSecondAttack() {
        return secondAttack;
    }

    public int getDifference() {
        return difference;
    }

    public String getWinner() {
        return winner;
    }

    public String getMessage() {
        return message;
    }
}
