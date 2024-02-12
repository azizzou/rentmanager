package com.epf.rentmanager.model;

public class Vehicle {
    private int id, nb_places ;
    private String constructeur , modele;


    public Vehicle() {

    }

    public Vehicle(int id, String constructeur, String modele , int nb_places ) {
        this.id = id;
        this.nb_places = nb_places;
        this.constructeur = constructeur;
        this.modele = modele;
    }

    public int getId() {
        return id;
    }

    public int getNb_places() {
        return nb_places;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }
}
