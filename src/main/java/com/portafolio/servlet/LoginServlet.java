package com.portafolio.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("usuario");
        String pass = request.getParameter("clave");
        String expectedUser = System.getenv().getOrDefault("ADMIN_USER", "admin");
        String expectedPass = System.getenv("ADMIN_PASSWORD");
        if (expectedPass != null && !expectedPass.isBlank()
                && expectedUser.equals(user) && expectedPass.equals(pass)) {
            request.getSession(true).setAttribute("admin", true);
            response.sendRedirect(request.getContextPath() + "/inicio");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos. Verifica las variables de entorno.");
            request.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(request, response);
        }
    }
}
