package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;
@Repository

public class VehicleDao {



	public VehicleDao() {}



	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";

	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());

			preparedStatement.execute();

			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			preparedStatement.setInt(1, vehicle.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return vehicle.getId();
	}

	public static Vehicle findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Vehicle vehicle = new Vehicle();
					vehicle.setId(resultSet.getInt("id"));
					vehicle.setConstructeur(resultSet.getString("constructeur"));
					vehicle.setModele(resultSet.getString("modele"));
					vehicle.setNb_places(resultSet.getInt("nb_places"));
					return vehicle;
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getInt("nb_places"));
				vehicles.add(vehicle);
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return vehicles;
	}
	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VEHICLES_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt("count");
			} else {
				throw new DaoException("Erreur compter nombre client .");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public long update(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VEHICLE_QUERY)) {

			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.setLong(4, vehicle.getId());

			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
