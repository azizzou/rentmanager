package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
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
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {



    @Autowired

    private ReservationService reservationService;
    private ClientService clientService;
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.reservationService = new ReservationService(new ReservationDao());
        this.reservationService = new ReservationService(new ReservationDao());
        this.clientService = new ClientService(new ClientDao());
        this.vehicleService = new VehicleService(new VehicleDao());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Client> clients = clientService.findAllClients();
            List<Vehicle> vehicles = vehicleService.findAllVehicles();

            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);

            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving data for reservation creation");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("client"));
            long vehicleId = Long.parseLong(request.getParameter("car"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate beginDate = LocalDate.parse(request.getParameter("begin"), formatter);
            LocalDate endDate = LocalDate.parse(request.getParameter("end"), formatter);

            Reservation reservation = new Reservation();
            reservation.setClient_id(clientId);
            reservation.setVehicle_id(vehicleId);
            reservation.setDebut(beginDate);
            reservation.setFin(endDate);


            long reservationId = reservationService.createReservation(reservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating reservation");
        }
    }
}
