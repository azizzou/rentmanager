// VehicleCreateServlet.java
package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;  // Add this import statement
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    @Autowired

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.vehicleService = new VehicleService(new VehicleDao(), new ReservationDao());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manufacturer = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int seats = Integer.parseInt(request.getParameter("seats"));

        Vehicle newVehicle = new Vehicle();
        newVehicle.setConstructeur(manufacturer);
        newVehicle.setModele(modele);
        newVehicle.setNb_places(seats);

        try {
            vehicleService.createVehicle(newVehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/cars");
    }
}
