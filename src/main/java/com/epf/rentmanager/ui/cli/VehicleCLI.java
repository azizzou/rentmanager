package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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
            IOUtils.print("Enter vehicle details:");

            String manufacturer = IOUtils.readString("Manufacturer: ", true);
            String model = IOUtils.readString("Model: ", true); // Add input for the model
            int seats = IOUtils.readInt("Number of seats: ", true);

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(manufacturer);
            vehicle.setModele(model); // Set the model property
            vehicle.setNb_places(seats);

            long vehicleId = vehicleService.createVehicle(vehicle);

            IOUtils.print("Vehicle created successfully with ID: " + vehicleId);
        } catch (ServiceException e) {
            System.err.println("Error creating vehicle: " + e.getMessage());
        }
    }

    public void listVehicles() {

        try {
            IOUtils.print("List of Vehicles:");

            List<Vehicle> vehicles = vehicleService.findAllVehicles();

            if (vehicles.isEmpty()) {
                IOUtils.print("No vehicles found.");
            } else {
                for (Vehicle vehicle : vehicles) {
                    IOUtils.print(vehicle.toString());
                }
            }
        } catch (ServiceException e) {
            System.err.println("Error listing vehicles: " + e.getMessage());
        }
    }
}
