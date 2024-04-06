package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private VehicleService vehicleService;
	private ReservationService reservationService;
	private ClientService clientService;

	@Override
	public void init() throws ServletException {
		vehicleService = new VehicleService(new VehicleDao(),new ReservationDao() );
		reservationService = new ReservationService(new ReservationDao());
		clientService = new ClientService(new ClientDao(), new ReservationDao());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws  IOException {
		try {
			int vehicleCount = vehicleService.countVehicles();
			request.setAttribute("vehicleCount", vehicleCount);
			int reservationCount = reservationService.countReservation();
			request.setAttribute("reservationCount", reservationCount);
			int clientCount = clientService.countClient();
			request.setAttribute("clientCount", clientCount);


			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {


			response.sendRedirect(request.getContextPath() + "/error");

		}
	}
}
