package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class VehicleCLI {

    private VehicleService vehicleService;

    public VehicleCLI(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.vehicleService = context.getBean(VehicleService.class);
    }

    public void createVehicle() {

        try {
            IOUtils.print("Entrer information véhicule:");

            String manufacturer = IOUtils.readString("constructeur: ", true);
            String model = IOUtils.readString("Model: ", true);
            int seats = IOUtils.readInt("nombre de siége: ", true);

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(manufacturer);
            vehicle.setModele(model);
            vehicle.setNb_places(seats);

            long vehicleId = vehicleService.createVehicle(vehicle);

            IOUtils.print("Vehicle créer avec  l'ID: " + vehicleId);
        } catch (ServiceException e) {
            System.err.println("Erreur création vehicle: " + e.getMessage());
        }
    }

    public void listVehicles() {

        try {
            IOUtils.print("Liste des Vehicles:");

            List<Vehicle> vehicles = vehicleService.findAllVehicles();

            if (vehicles.isEmpty()) {
                IOUtils.print("aucun véhicule trouvé .");
            } else {
                for (Vehicle vehicle : vehicles) {
                    IOUtils.print(vehicle.toString());
                }
            }
        } catch (ServiceException e) {
            System.err.println("Erreur liste véhicule: " + e.getMessage());
        }
    }
}
