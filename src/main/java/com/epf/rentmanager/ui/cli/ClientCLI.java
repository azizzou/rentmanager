package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.DaoException;
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

    public void createClient() throws SQLException {


        try {

            String nom = IOUtils.readString("Nom client: ", true);
            String prenom = IOUtils.readString("prénom client: ", true);
            String email = IOUtils.readString("Client email: ", true);
            LocalDate naissance = IOUtils.readDate("date de naissance (yyyy-MM-dd): ", true);



            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(Date.valueOf(naissance).toLocalDate());

            System.out.println("Client object a été crée");

            long clientId = clientService.createClient(client);

            System.out.println("Client crée avec ID: " + clientId);
        } catch (ServiceException | DaoException e) {
            System.err.println("Erreur création client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listClients() {
        try {

            // Call service to get all clients
            System.out.println("Liste des Clients:");
            clientService.findAllClients().forEach(System.out::println);
        } catch (ServiceException e) {
            System.err.println("erreur liste clients: " + e.getMessage());
        }
    }
}
