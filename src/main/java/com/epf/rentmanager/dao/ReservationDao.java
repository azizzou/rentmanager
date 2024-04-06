package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;
@Repository
public class ReservationDao {


	public ReservationDao() {}


	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
	private static final String FIND_RESERVATION_BY_ID_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, reservation.getClient_id());
			preparedStatement.setInt(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));

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

	public long delete(Reservation reservation) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {

			preparedStatement.setInt(1, reservation.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DaoException( e);
		}
		return reservation.getId();
	}


	public List<Reservation> findResaByClientId(long clientId) throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {

			preparedStatement.setLong(1, clientId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(resultSet.getInt("id"));
					reservation.setClient_id(clientId);
					reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
					reservation.setDebut(resultSet.getDate("debut").toLocalDate());
					reservation.setFin(resultSet.getDate("fin").toLocalDate());
					Vehicle vehicle = VehicleDao.findById(reservation.getVehicle_id());
					Client client = ClientDao.findById(reservation.getClient_id());
					reservation.setClient(client);
					reservation.setVehicle(vehicle);
					reservations.add(reservation);
				}
			}

		} catch (SQLException e) {
			throw new DaoException( e);
		}

		return reservations;
	}
	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt("count");
			} else {
				throw new DaoException("Error counting reservation. No result.");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, vehicleId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(resultSet.getInt("id"));
					reservation.setClient_id(resultSet.getInt("client_id"));
					reservation.setVehicle_id(vehicleId);
					Vehicle vehicle = VehicleDao.findById(reservation.getVehicle_id());
					reservation.setVehicle(vehicle);
					Client client = ClientDao.findById(reservation.getClient_id());
					reservation.setClient(client);
					reservation.setDebut(resultSet.getDate("debut").toLocalDate());
					reservation.setFin(resultSet.getDate("fin").toLocalDate());
					reservations.add(reservation);
				}
			}

		} catch (SQLException e) {
			throw new DaoException( e);
		}

		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {

		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setClient_id(resultSet.getInt("client_id"));
				reservation.setVehicle_id(resultSet.getInt("vehicle_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				Vehicle vehicle = VehicleDao.findById(reservation.getVehicle_id());
				Client client = ClientDao.findById(reservation.getClient_id());
				reservation.setClient(client);
				reservation.setVehicle(vehicle);
				reservations.add(reservation);
			}

		} catch (SQLException e) {
			throw new DaoException( e);
		}

		return reservations;
	}
	public long delete(long reservationId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {

			preparedStatement.setLong(1, reservationId);
			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	public Reservation findById(long reservationId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_BY_ID_QUERY)) {

			preparedStatement.setLong(1, reservationId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Reservation reservation = new Reservation();
					reservation.setId(resultSet.getLong("id"));
					reservation.setClient_id(resultSet.getLong("client_id"));
					reservation.setVehicle_id(resultSet.getLong("vehicle_id"));
					reservation.setDebut(resultSet.getDate("debut").toLocalDate());
					reservation.setFin(resultSet.getDate("fin").toLocalDate());
					return reservation;
				} else {
					throw new DaoException("Reservation not found with ID: " + reservationId);
				}
			}

		} catch (SQLException e) {
			throw new DaoException("Error finding reservation by ID: " + e.getMessage(), e);
		}
	}

	public void update(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY)) {

			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.setLong(5, reservation.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Erreur mise à jour réservation: " + e.getMessage(), e);
		}
	}
}


