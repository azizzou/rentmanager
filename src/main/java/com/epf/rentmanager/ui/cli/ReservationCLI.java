package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationCLI {

    private ReservationService reservationService;

    public ReservationCLI(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void createReservation() {
        try {
            long clientId = IOUtils.readInt("Client ID: ", true);
            long vehicleId = IOUtils.readInt(" Vehicle ID: ", true);
            LocalDate debut = IOUtils.readDate("  date début(dd/MM/yyyy): ", true);
            LocalDate fin = IOUtils.readDate("  date fin (dd/MM/yyyy): ", true);

            Reservation reservation = new Reservation();
            reservation.setClient_id(clientId);
            reservation.setVehicle_id(vehicleId);
            reservation.setDebut(debut);
            reservation.setFin(fin);

            long reservationId = reservationService.createReservation(reservation);

            System.out.println("Reservation faite avec id : " + reservationId);
        } catch (ServiceException e) {
            System.err.println("Errur création reservation: " + e.getMessage());
        }
    }

    public void listAllReservations() {
        try {
            System.out.println("Liste des Reservations:");
            List<Reservation> reservations = reservationService.findAllReservations();


            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Erreur liste des reservations: " + e.getMessage());
        }
    }

    public void listReservationsByClient() {
        try {
            long clientId = IOUtils.readInt("Entrez Client ID: " , true);

            List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

            System.out.println("Liste réservation avec client ID " + clientId + ":");
            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Erreur liste avec client id: " + e.getMessage());
        }
    }

    public void listReservationsByVehicle() {
        try {
            long vehicleId = IOUtils.readInt("entrez Vehicle ID: " , true);

            List<Reservation> reservations = reservationService.findReservationsByVehicleId(vehicleId);

            System.out.println("Liste réservation avec véhicule ID " + vehicleId + ":");
            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Erreur liste véhicule: " + e.getMessage());
        }
    }

    public void deleteReservation() {
        try {
            long reservationId = IOUtils.readInt("entrer Id de la réservation  à supprimer: ", true);

            reservationService.deleteReservation(reservationId);


        } catch (ServiceException e) {
            System.err.println("erreur suppression reservation: " + e.getMessage());
        }
    }
}