package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

    private int id ;
    private int client_id ;
    private int vehicle_id;
    private LocalDate debut ;
    private LocalDate fin ;
    private Client client;
    private Vehicle vehicle;

    public Reservation() {
    }
    public Reservation(int id, int client_id, int vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id =  new Integer(Math.toIntExact(id));
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public void setClient_id (int client_id) {
        this.client_id = client_id;
    }
    public void setClient_id(long client_id) {
        this.client_id =  new Integer(Math.toIntExact(client_id));
    }

    public void setVehicle_id (int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id =  new Integer(Math.toIntExact(vehicle_id));
    }

    public Client getClient() {
        return client;
    }

    public void setDebut (LocalDate debut) {
        this.debut = debut;
    }
    public void setFin (LocalDate fin) {
        this.fin = fin;
    }
    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +

                '}';
    }
}
