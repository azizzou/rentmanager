package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import java.sql.Date;
import java.time.LocalDate;

public class ClientCLI {


    private ClientService clientService;

    public ClientCLI(ClientService clientService) {
        this.clientService = clientService;
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.clientService = context.getBean(ClientService.class);
    }

    public void createClient() throws ServiceException, SQLException {


        try {

            String nom = IOUtils.readString("Enter client's last name: ", true);
            String prenom = IOUtils.readString("Enter client's first name: ", true);
            String email = IOUtils.readString("Enter client's email: ", true);
            LocalDate naissance = IOUtils.readDate("Enter client's birthdate (yyyy-MM-dd): ", true);



            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(Date.valueOf(naissance).toLocalDate());

            System.out.println("Client object created. Calling service to create client...");

            long clientId = clientService.createClient(client);

            System.out.println("Client created successfully with ID: " + clientId);
        } catch (ServiceException e) {
            System.err.println("Error creating client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listClients() {
        try {

            // Call service to get all clients
            System.out.println("List of Clients:");
            clientService.findAllClients().forEach(System.out::println);
        } catch (ServiceException e) {
            System.err.println("Error listing clients: " + e.getMessage());
        }
    }
}
