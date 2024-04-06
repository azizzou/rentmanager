package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
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
	public long createClient(Client client) throws ServiceException, SQLException, DaoException {
		validateClient(client);

		if (calculateAge(client) ) {
			throw new ServiceException("Le client doit avoir au moins 18 ans.");
		}

		if (isNameValid(client)) {
			throw new ServiceException("Le nom et le prénom du client doivent faire au moins 3 caractères.");
		}

		if (emailExists(client) ){
			throw new ServiceException("L'adresse email est déjà utilisée par un autre client.");
		}

		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du client : " + e.getMessage());
		}
	}
	private boolean calculateAge(Client client) {
		LocalDate now = LocalDate.now();
		Period period = Period.between(client.getNaissance(), now);
		return period.getYears() >= 18;
    }

	private boolean emailExists( Client client) throws  DaoException {

			List<Client> clients = clientDao.findAll();
			for (Client Clientreche : clients) {
				if (Clientreche.getEmail().equals(client.getEmail())) {
					return true;
				}
			}
			return false;

	}
	private boolean isNameValid(Client client) {
		return client.getNom().length() >= 3&& client.getPrenom().length() >= 3;
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
