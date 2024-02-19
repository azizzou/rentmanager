package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException, SQLException {
		Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
		try {
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId());
			preparedStatement.setString(2, client.getNom());
			preparedStatement.setString(3, client.getPrenom());
			preparedStatement.setString(4, client.getEmail());
			preparedStatement.setDate(5, Date.valueOf(client.getNaissance()));


			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
			connexion.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} return 0;
	}
	
	public long delete(Client client) throws DaoException, SQLException {
		Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
		try {
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId());



			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			connexion.close();
			return client.getId() ;
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}

	public Client s(long id) throws DaoException, SQLException {

		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {

			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Client client = new Client();
					client.setId(id);
					client.setNom(resultSet.getString("nom"));
					client.setPrenom(resultSet.getString("prenom"));
					client.setEmail(resultSet.getString("email"));
					client.setNaissance(resultSet.getDate("naissance").toLocalDate());

					return client;
				} else {

					return null;
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Client client = new Client();
				client.setId(resultSet.getLong("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());

				clients.add(client);
			}

		} catch (SQLException e) {
			throw new DaoException( e);
		}

		return clients;
	}
}
