package com.ensta.rentmanager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;


import com.epf.rentmanager.dao.DaoException;

import org.junit.Before;
import org.junit.Test;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;

public class ClientServiceTest {

    private ClientDao clientDao;
    private ClientService clientService;

    @Before
    public void setUp() {
        clientDao = mock(ClientDao.class);
        clientService = new ClientService(clientDao);
    }

    @Test
    public void testCreateClient() throws ServiceException, DaoException, SQLException {
        Client client = new Client();
        client.setNom("aziz");
        client.setPrenom("oulalala");

        when(clientDao.create(client)).thenReturn(1L);

        long clientId = clientService.createClient(client);

        assertEquals(1L, clientId);
    }

    @Test(expected = ServiceException.class)
    public void testCreateClientWithInvalidData() throws ServiceException, SQLException, DaoException {
        Client client = new Client();

        clientService.createClient(client);
    }

    @Test
    public void testDeleteClient() throws ServiceException, SQLException, DaoException {
        Client client = new Client();
        client.setId(1L);

        when(clientDao.delete(client)).thenReturn(1L);

        long deletedClientId = clientService.deleteClient(client);

        assertEquals(1L, deletedClientId);
    }

    @Test
    public void testFindClientById() throws ServiceException, SQLException, DaoException {
        Client client = new Client();
        client.setId(1L);
        client.setNom("aziz");
        client.setPrenom("oulalala");

        when(clientDao.findById(1L)).thenReturn(client);

        Client foundClient = clientService.findClientById(1L);

        assertNotNull(foundClient);
        assertEquals("aziz", foundClient.getNom());
        assertEquals("oulalala", foundClient.getPrenom());
    }



}


