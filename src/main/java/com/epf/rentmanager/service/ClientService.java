package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.epf.rentmanager.dao.DaoException;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	public long createClient(Client client) throws ServiceException, SQLException {
		validateClient(client);
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Error creating client", e);
		}
	}

	public long deleteClient(Client client) throws ServiceException {
		try {
			return clientDao.delete(client);
		} catch (SQLException | DaoException e) {
			throw new ServiceException("Error deleting client: " + e.getMessage());
		}
	}

	public Client findClientById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (SQLException | DaoException e) {
			throw new ServiceException("Error finding client: " + e.getMessage());
		}
	}

	public List<Client> findAllClients() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Error finding all clients: " + e.getMessage());
		}
	}

	private static void validateClient(Client client) throws ServiceException {
		if (client.getNom().isEmpty() || client.getPrenom().isEmpty()) {
			throw new ServiceException("Client name and first name cannot be empty.");
		}
	}
	public int countClient() throws ServiceException {
		try {
			return ClientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Error counting reservations: " + e.getMessage(), e);
		}
	}
	public long updateClient(Client client) throws ServiceException {
		validateClient(client); // Assurez-vous que les données du client sont valides avant la mise à jour
		try {
			return clientDao.update(client);
		} catch (DaoException e) {
			throw new ServiceException("Error updating client", e);
		}
	}

}
