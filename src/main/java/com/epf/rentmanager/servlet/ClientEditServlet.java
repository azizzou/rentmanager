package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String clientIdParam = request.getParameter("id");
        if (clientIdParam == null || clientIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users"); // Redirige vers la liste des utilisateurs si l'identifiant n'est pas spécifié
            return;
        }

        try {
            long clientId = Long.parseLong(clientIdParam);
            Client client = clientService.findClientById(clientId);
            if (client != null) {
                request.setAttribute("client", client);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/users"); // Redirige vers la liste des utilisateurs si l'utilisateur n'est pas trouvé
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/users"); // Redirige vers la liste des utilisateurs si l'identifiant n'est pas un nombre valide
        } catch (ServiceException e) {
            throw new ServletException("Error fetching client for edit", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String clientIdParam = request.getParameter("id");
        if (clientIdParam == null || clientIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users"); // Redirige vers la liste des utilisateurs si l'identifiant n'est pas spécifié
            return;
        }

        try {
            long clientId = Long.parseLong(clientIdParam);
            Client existingClient = clientService.findClientById(clientId);
            if (existingClient != null) {
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                String email = request.getParameter("email");
                String birthdate = request.getParameter("birthdate");

                existingClient.setNom(lastName);
                existingClient.setPrenom(firstName);
                existingClient.setEmail(email);
                existingClient.setNaissance(LocalDate.parse(birthdate));

                clientService.updateClient(existingClient);
            }
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/users"); // Redirige vers la liste des utilisateurs si l'identifiant n'est pas un nombre valide
        } catch (ServiceException e) {
            throw new ServletException("Error updating client", e);
        }
    }
}
