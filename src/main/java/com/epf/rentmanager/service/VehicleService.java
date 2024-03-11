package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public long createVehicle(Vehicle vehicle) throws ServiceException {
		validateVehicle(vehicle);
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Error creating vehicle: " + e.getMessage());
		}
	}

	public long deleteVehicle(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Error deleting vehicle: " + e.getMessage());
		}
	}

	public Vehicle findVehicleById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Error finding vehicle: " + e.getMessage());
		}
	}

	public List<Vehicle> findAllVehicles() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Error fetching all vehicles: " + e.getMessage(), e);
		}
	}

	private void validateVehicle(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur().isEmpty() || vehicle.getNb_places() <= 1) {
			throw new ServiceException("Vehicle manufacturer cannot be empty, and the number of places should be greater than 1.");
		}
	}
	public int countVehicles() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Error counting vehicles: " + e.getMessage(), e);
		}
	}
}