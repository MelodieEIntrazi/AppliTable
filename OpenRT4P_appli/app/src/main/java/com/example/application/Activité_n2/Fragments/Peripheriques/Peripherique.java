package com.example.application.Activité_n2.Fragments.Peripheriques;

public class Peripherique {
    public String nom;
    public boolean connecte;

    public Peripherique(String nom, boolean connecte) {
        this.nom = nom;
        this.connecte = connecte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isConnecte() {
        return connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }
}
