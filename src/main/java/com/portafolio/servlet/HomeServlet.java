package com.portafolio.servlet;

import com.mongodb.client.gridfs.GridFSBucket;
import com.portafolio.config.AppUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/inicio"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GridFSBucket bucket = AppUtil.bucket(getServletContext());
        long total = 0;
        if (bucket != null) {
            try (var cursor = bucket.find().iterator()) {
                while (cursor.hasNext()) {
                    cursor.next();
                    total++;
                }
            } catch (Exception ex) {
                getServletContext().log("No se pudo consultar GridFS.", ex);
            }
        }
        request.setAttribute("totalTrabajos", total);
        request.getRequestDispatcher("/WEB-INF/vistas/inicio.jsp").forward(request, response);
    }
}