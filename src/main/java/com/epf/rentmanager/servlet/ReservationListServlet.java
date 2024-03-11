package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.reservationService = new ReservationService(new ReservationDao());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reservation> reservations = reservationService.findAllReservations();

            request.setAttribute("reservations", reservations);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving reservations");
        }
    }
}
