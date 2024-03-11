package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {
    private ClientService clientService;
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        clientService = new ClientService(new ClientDao());
        reservationService = new ReservationService(new ReservationDao());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findClientById(clientId);
            List<Reservation> reservations = reservationService.findReservationsByClientId(clientId);

            request.setAttribute("client", client);
            request.setAttribute("reservations", reservations);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            throw new ServletException("Error fetching client details", e);
        }
    }
}
