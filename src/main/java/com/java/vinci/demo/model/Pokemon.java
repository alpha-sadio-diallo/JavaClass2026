package com.java.vinci.demo.model;

public class Pokemon {
    private Long id;
    private String name;
    private String taille;
     private String poids;

    private String[] type;
    private String[] capacites;
    private Statistiques stats;
    private String imageUrl;



    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and setters for types field
    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }



    public String[] getCapacites() {
        return capacites;
    }

    public void setCapacites(String[] capacites) {
        this.capacites = capacites;
    }

    public Statistiques getStats() {
        return stats;
    }
    public void setStats(Statistiques stats) {
        this.stats = stats;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String url) {
        this.imageUrl = url;
    }


}