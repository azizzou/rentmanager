package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.vehicleService = new VehicleService(new VehicleDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Vehicle> vehicles = vehicleService.findAllVehicles();
            request.setAttribute("vehicles", vehicles);

            request.getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving vehicle list");
        }
    }
}
