package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao;

	public VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao) {

		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao ;
	}

	public long createVehicle(Vehicle vehicle) throws ServiceException {
		validateVehicle(vehicle);
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur création vehicle: " + e.getMessage());
		}
	}

	public long deleteVehicle(Vehicle vehicle) throws ServiceException {
		try {
			List<Reservation> reservations = reservationDao.findResaByVehicleId(vehicle.getId());
			for (Reservation reservation : reservations) {
				reservationDao.delete(reservation.getId());
			}
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur supprésion vehicle: " + e.getMessage());
		}

	}

	public Vehicle findVehicleById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur trouver vehicle: " + e.getMessage());
		}
	}

	public List<Vehicle> findAllVehicles() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Error trouver tout vehicles: " + e.getMessage(), e);
		}
	}

	private void validateVehicle(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur().isEmpty() || vehicle.getModele().isEmpty()) {
			throw new ServiceException("Le constructeur et le modèle du véhicule ne peuvent pas être vides.");
		}
		if (vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9) {
			throw new ServiceException("Le nombre de places du véhicule doit être compris entre 2 et 9.");
		}
	}
	public int countVehicles() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur compter vehicles: " + e.getMessage(), e);
		}
	}
	public long updateVehicle(Vehicle vehicle) throws ServiceException {
		validateVehicle(vehicle);
		try {
			return vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur mise à jour vehicle: " + e.getMessage());
		}
	}

}