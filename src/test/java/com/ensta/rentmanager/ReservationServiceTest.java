package com.ensta.rentmanager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



import org.junit.Before;
import org.junit.Test;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;

public class ReservationServiceTest {

    private ReservationDao reservationDao;
    private ReservationService reservationService;

    @Before
    public void setUp() {
        reservationDao = mock(ReservationDao.class);
        reservationService = new ReservationService(reservationDao);
    }

    @Test
    public void testCreateReservation() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        when(reservationDao.create(reservation)).thenReturn(1L);
        long reservationId = reservationService.createReservation(reservation);
        assertEquals(1L, reservationId);
    }

    @Test(expected = ServiceException.class)
    public void testCreateReservationWithInvalidData() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        reservationService.createReservation(reservation);
    }


}
