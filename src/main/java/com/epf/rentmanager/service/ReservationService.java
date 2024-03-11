package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long createReservation(Reservation reservation) throws ServiceException {
        try {
            // Add any necessary validation logic
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Error creating reservation: " + e.getMessage());
        }
    }

    public List<Reservation> findAllReservations() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error finding all reservations: " + e.getMessage());
        }
    }

    public List<Reservation> findReservationsByClientId(long clientId) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Error finding reservations by client ID: " + e.getMessage());
        }
    }

    public List<Reservation> findReservationsByVehicleId(long vehicleId) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException("Error finding reservations by vehicle ID: " + e.getMessage());
        }
    }

    public void deleteReservation(long reservationId) throws ServiceException {
        try {
            long rowsAffected = reservationDao.delete(reservationId);
            if (rowsAffected == 0) {
                throw new ServiceException("Reservation not found with ID: " + reservationId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error deleting reservation: " + e.getMessage());
        }
    }
    public int countReservation() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Error counting reservations: " + e.getMessage(), e);
        }
    }
}
