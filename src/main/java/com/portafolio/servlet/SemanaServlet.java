package com.portafolio.servlet;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.portafolio.config.AppUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/semana")
public class SemanaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numero;
        try { numero = Integer.parseInt(request.getParameter("n")); }
        catch (Exception ex) { numero = 1; }
        if (numero < 1 || numero > 16) numero = 1;

        List<GridFSFile> trabajos = new ArrayList<>();
        GridFSBucket bucket = AppUtil.bucket(getServletContext());
        if (bucket != null) {
            try (var cursor = bucket.find(new org.bson.Document("metadata.semana", numero)).iterator()) {
                while (cursor.hasNext()) trabajos.add(cursor.next());
            } catch (Exception ex) {
                getServletContext().log("Error consultando trabajos de la semana " + numero, ex);
            }
        }
        request.setAttribute("numeroSemana", numero);
        request.setAttribute("trabajos", trabajos);
        request.getRequestDispatcher("/WEB-INF/vistas/semana.jsp").forward(request, response);
    }
}
