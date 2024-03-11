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
            // Gather reservation information from user input
            // Similar to how you gathered client and vehicle information in other parts of your CLI
            long clientId = IOUtils.readInt("Enter Client ID: ", true);
            long vehicleId = IOUtils.readInt("Enter Vehicle ID: ", true);
            LocalDate debut = IOUtils.readDate("Enter start date (dd/MM/yyyy): ", true);
            LocalDate fin = IOUtils.readDate("Enter end date (dd/MM/yyyy): ", true);
            // Other reservation details...

            // Create Reservation object
            Reservation reservation = new Reservation();
            reservation.setClient_id(clientId);
            reservation.setVehicle_id(vehicleId);
            reservation.setDebut(debut);
            reservation.setFin(fin);
            // Set other reservation details...

            // Call service to create reservation
            long reservationId = reservationService.createReservation(reservation);

            System.out.println("Reservation created successfully with ID: " + reservationId);
        } catch (ServiceException e) {
            System.err.println("Error creating reservation: " + e.getMessage());
        }
    }

    public void listAllReservations() {
        try {
            System.out.println("List of Reservations:");
            List<Reservation> reservations = reservationService.findAllReservations();


            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Error listing all reservations: " + e.getMessage());
        }
    }

    public void listReservationsByClient() {
        try {
            // Gather client information from user input
            long clientId = IOUtils.readInt("Enter Client ID: " , true);

            // Call service to get reservations by client
            List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

            System.out.println("List of Reservations for Client ID " + clientId + ":");
            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Error listing reservations by client: " + e.getMessage());
        }
    }

    public void listReservationsByVehicle() {
        try {
            // Gather vehicle information from user input
            long vehicleId = IOUtils.readInt("Enter Vehicle ID: " , true);

            // Call service to get reservations by vehicle
            List<Reservation> reservations = reservationService.findReservationsByVehicleId(vehicleId);

            System.out.println("List of Reservations for Vehicle ID " + vehicleId + ":");
            IOUtils.print(reservations.toString());
        } catch (ServiceException e) {
            System.err.println("Error listing reservations by vehicle: " + e.getMessage());
        }
    }

    public void deleteReservation() {
        try {
            // Gather reservation information from user input
            long reservationId = IOUtils.readInt("Enter Reservation ID to delete: ", true);

            // Call service to delete reservation
            reservationService.deleteReservation(reservationId);


        } catch (ServiceException e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
        }
    }
}