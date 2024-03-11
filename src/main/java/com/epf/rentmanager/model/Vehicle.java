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
    public Vehicle(int id ) {
        this.id =  id;

    }
    public Vehicle(long id ) {
        this.id =  (int) id;

    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id =  new Integer(Math.toIntExact(id));
    }
    public void setConstructeur (String constructeur) {
        this.constructeur = constructeur;
    }
    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
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
    @Override
    public String toString() {
        return "Vehicle{id=" + id + ", constructeur='" + constructeur + "', nb_places=" + nb_places + ", modele='" + modele + "'}";
    }
}
