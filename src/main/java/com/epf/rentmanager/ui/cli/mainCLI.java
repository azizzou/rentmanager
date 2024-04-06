package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

import java.sql.SQLException;

public class mainCLI {

    public static void main(String[] args) throws ServiceException, SQLException {
        ClientDao clientDao = new ClientDao();
        VehicleDao vehicleDao = new VehicleDao();
        ReservationDao reservationDao = new ReservationDao();


        ClientService clientService = new ClientService(clientDao, reservationDao);
        VehicleService vehicleService = new VehicleService(vehicleDao, reservationDao);
        ReservationService reservationService = new ReservationService(reservationDao);

        ClientCLI clientCLI = new ClientCLI(clientService);
        VehicleCLI vehicleCLI = new VehicleCLI(vehicleService);
        ReservationCLI reservationCLI = new ReservationCLI(reservationService);

        startCLI(clientCLI, vehicleCLI, reservationCLI);
    }

    private static void startCLI(ClientCLI clientCLI, VehicleCLI vehicleCLI, ReservationCLI reservationCLI) throws ServiceException, SQLException {
        while (true) {
            displayMainMenu();

            int choice = IOUtils.readInt("entrée votre choix: ", true);

            switch (choice) {
                case 1:
                    startClientMenu(clientCLI);
                    break;
                case 2:
                    startVehicleMenu(vehicleCLI);
                    break;
                case 3:
                    startReservationMenu(reservationCLI);
                    break;
                case 4:
                    System.out.println("bye bye ");
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide, choisi bien.");
            }
        }
    }

    private static void startClientMenu(ClientCLI clientCLI) throws ServiceException, SQLException {
        while (true) {
            displayClientMenu();

            int choice = IOUtils.readInt("entrez votre choix: ", true);

            switch (choice) {
                case 1:
                    clientCLI.createClient();
                    break;
                case 2:
                    clientCLI.listClients();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("choix invalide, choisi bien.");
            }
        }
    }

    private static void startVehicleMenu(VehicleCLI vehicleCLI) {
        while (true) {
            displayVehicleMenu();

            int choice = IOUtils.readInt(" choisis: ", true);

            switch (choice) {
                case 1:
                    vehicleCLI.createVehicle();
                    break;
                case 2:
                    vehicleCLI.listVehicles();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("choix invalide, choisi bien.");
            }
        }
    }

    private static void startReservationMenu(ReservationCLI reservationCLI) {
        while (true) {
            displayReservationMenu();

            int choice = IOUtils.readInt("Enter your choice: ", true);

            switch (choice) {
                case 1:
                    reservationCLI.createReservation();
                    break;
                case 2:
                    reservationCLI.listAllReservations();
                    break;
                case 3:
                    reservationCLI.listReservationsByClient();
                    break;
                case 4:
                    reservationCLI.listReservationsByVehicle();
                    break;
                case 5:
                    reservationCLI.deleteReservation();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("choix invalide, choisi bien");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Client Menu");
        System.out.println("2. Vehicle Menu");
        System.out.println("3. Reservation Menu");
        System.out.println("4. Exit");
    }

    private static void displayClientMenu() {
        System.out.println("Client Menu:");
        System.out.println("1. Create Client");
        System.out.println("2. List All Clients");
        System.out.println("3. Back to Main Menu");
    }

    private static void displayVehicleMenu() {
        System.out.println("Vehicle Menu:");
        System.out.println("1. Create Vehicle");
        System.out.println("2. List All Vehicles");
        System.out.println("3. Back to Main Menu");
    }

    private static void displayReservationMenu() {
        System.out.println("Reservation Menu:");
        System.out.println("1. Create Reservation");
        System.out.println("2. List All Reservations");
        System.out.println("3. List Reservations by Client");
        System.out.println("4. List Reservations by Vehicle");
        System.out.println("5. Delete Reservation");
        System.out.println("6. Back to Main Menu");
    }
}
