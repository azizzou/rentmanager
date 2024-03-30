package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;
@Repository
public class ClientDao {
	

	public ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
private static final String UPDATE_CLIENT = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance())); // This line seems to be causing the issue

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("Creating client failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("Creating client failed, no ID obtained." );
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error creating client", e);
		}
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

	public static Client findById(long id) throws DaoException, SQLException {

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
	public static int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt("count");
			} else {
				throw new DaoException("Error counting vehicles. No result.");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public long update(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT)) {

			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.setLong(5, client.getId());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("Updating client failed, no rows affected.");
			}
			return client.getId();
		} catch (SQLException e) {
			throw new DaoException("Error updating client", e);
		}
	}
}
