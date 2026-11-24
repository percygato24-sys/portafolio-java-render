package com.portafolio.servlet;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.portafolio.config.AppUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.bson.Document;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@WebServlet("/subir")
@MultipartConfig(maxFileSize = 20 * 1024 * 1024, maxRequestSize = 22 * 1024 * 1024)
public class SubirTrabajoServlet extends HttpServlet {
    private static final Set<String> TIPOS = Set.of(
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "image/jpeg", "image/png"
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AppUtil.isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        int semana;
        try { semana = Integer.parseInt(request.getParameter("semana")); }
        catch (Exception ex) { semana = 0; }
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        Part archivo = request.getPart("archivo");

        if (semana < 1 || semana > 16 || titulo == null || titulo.isBlank()
                || archivo == null || archivo.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/semana?n=" + Math.max(1, Math.min(16, semana)) + "&estado=datos");
            return;
        }
        String tipo = archivo.getContentType() == null ? "" : archivo.getContentType();
        if (!TIPOS.contains(tipo) || archivo.getSize() > 20L * 1024 * 1024) {
            response.sendRedirect(request.getContextPath() + "/semana?n=" + semana + "&estado=tipo");
            return;
        }
        GridFSBucket bucket = AppUtil.bucket(getServletContext());
        if (bucket == null) {
            response.sendRedirect(request.getContextPath() + "/semana?n=" + semana + "&estado=bd");
            return;
        }

        String nombre = java.nio.file.Paths.get(archivo.getSubmittedFileName()).getFileName().toString();
        Document metadata = new Document("semana", semana)
                .append("titulo", titulo.trim())
                .append("descripcion", descripcion == null ? "" : descripcion.trim())
                .append("contentType", tipo);
        GridFSUploadOptions options = new GridFSUploadOptions().metadata(metadata);
        try (InputStream input = archivo.getInputStream()) {
            bucket.uploadFromStream(nombre, input, options);
            response.sendRedirect(request.getContextPath() + "/semana?n=" + semana + "&estado=ok");
        } catch (Exception ex) {
            getServletContext().log("No se pudo guardar el archivo.", ex);
            response.sendRedirect(request.getContextPath() + "/semana?n=" + semana + "&estado=bd");
        }
    }
}
