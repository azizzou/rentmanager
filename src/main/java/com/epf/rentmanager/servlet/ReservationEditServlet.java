package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {

    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById(reservationId);

            // Pass the start and end dates as request attributes
            request.setAttribute("beginDate", reservation.getDebut());
            request.setAttribute("endDate", reservation.getFin());

            // Forward the request to the create.jsp
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving reservation for editing");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("id"));
            long clientId = Long.parseLong(request.getParameter("client"));
            long vehicleId = Long.parseLong(request.getParameter("car"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate beginDate = LocalDate.parse(request.getParameter("begin"), formatter);
            LocalDate endDate = LocalDate.parse(request.getParameter("end"), formatter);

            Reservation reservation = new Reservation();
            reservation.setId(reservationId);
            reservation.setClient_id(clientId);
            reservation.setVehicle_id(vehicleId);
            reservation.setDebut(beginDate);
            reservation.setFin(endDate);

            reservationService.updateReservation(reservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating reservation");
        }
    }
}
