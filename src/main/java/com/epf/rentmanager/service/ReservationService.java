package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.time.LocalDate;
import java.time.Period;
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
            if (reservationExistsOnDate(reservation)) {
                throw new ServiceException("Cette voiture est déjà réservée pour la date spécifiée.");
            }

            if (calculateMaxConsecutiveDays(reservation)) {
                throw new ServiceException("La voiture ne peut pas être réservée plus de 30 jours de suite sans pause.");
            }
            if (!calculatetemps(reservation)) {
                throw new ServiceException("une location ne peut pas durer 7 jours.");
            }
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur création reservation: " + e.getMessage());
        }
    }
    public boolean reservationExistsOnDate(Reservation reservation) throws DaoException {
        try {
            List<Reservation> reservations = reservationDao.findAll();
            for (Reservation existingReservation : reservations) {
                if (existingReservation.getVehicle_id() == reservation.getVehicle_id() &&
                        (existingReservation.getDebut().isEqual(reservation.getDebut()) ||
                                existingReservation.getFin().isEqual(reservation.getFin()))) {
                    return true;
                }
            }
            return false;
        } catch (DaoException e) {
            throw new DaoException("erreur date réservation: " + e.getMessage());
        }
    }
    private boolean calculatetemps(Reservation reservation) {

        Period period = Period.between(reservation.getFin(), reservation.getDebut());
        return period.getDays() <= 7;
    }
    private boolean calculateMaxConsecutiveDays(Reservation reservation) throws DaoException {

        List<Reservation> reservations = reservationDao.findResaByVehicleId(reservation.getVehicle_id());

        for (Reservation touteresa : reservations) {
            if (touteresa.getId() != reservation.getId()) {
                LocalDate fin = touteresa.getFin();
                if (reservation.getDebut().isAfter(fin)) {
                    continue;
                }
                long daysBetween = Period.between(fin, reservation.getFin()).getDays();
                if (daysBetween <= 30) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<Reservation> findAllReservations() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur trouver reservations: " + e.getMessage());
        }
    }
    public List<Reservation> findReservationsByClientId(long clientId) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Error trouver toute les reservations pour client ID: " + e.getMessage());
        }
    }

    public List<Reservation> findReservationsByVehicleId(long vehicleId) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException("Error trouver toute les reservations pour vehicle ID: " + e.getMessage());
        }
    }

    public void deleteReservation(long reservationId) throws ServiceException {
        try {
            long rowsAffected = reservationDao.delete(reservationId);
            if (rowsAffected == 0) {
                throw new ServiceException("Reservation not found with ID: " + reservationId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error suprresion reservation: " + e.getMessage());
        }
    }
    public int countReservation() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur décompte reservations: " + e.getMessage(), e);
        }
    }
    public Reservation findById(long reservationId) throws ServiceException {
        try {
            return reservationDao.findById(reservationId);
        } catch (DaoException e) {
            throw new ServiceException("erreur pour trouver reservation avec  ID: " + e.getMessage());
        }
    }

    public void updateReservation(Reservation reservation) throws ServiceException {
        try {
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur mise à jour reservation: " + e.getMessage());
        }
    }
}
