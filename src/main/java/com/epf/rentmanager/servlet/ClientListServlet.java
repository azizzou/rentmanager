package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class ClientListServlet extends HttpServlet {
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        clientService = new ClientService(new ClientDao());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> clients = clientService.findAllClients();
            request.setAttribute("clients", clients);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/list.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Error listing clients", e);
        }
    }
}