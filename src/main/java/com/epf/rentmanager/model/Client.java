package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate ;



public class Client {
private int id ;
private String nom , prenom, email;
private LocalDate naissance;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id =  new Integer(Math.toIntExact(id));
    }

    public void setNom (String nom) {
        this.nom = nom;
    }
    public void setPrenom (String nom) {
        this.prenom = prenom;
    }
    public void setEmail (String nom) {
        this.email = email;
    }
    public void setNaissance (LocalDate naissance) {
        this.naissance = naissance;
    }

    public Client() {

    }
    public Client(int id , String nom , String prenom ,String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }
}

