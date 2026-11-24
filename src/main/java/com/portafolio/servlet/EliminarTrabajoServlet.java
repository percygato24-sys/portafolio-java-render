package com.portafolio.servlet;

import com.mongodb.client.gridfs.GridFSBucket;
import com.portafolio.config.AppUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.bson.types.ObjectId;
import java.io.IOException;

@WebServlet("/eliminar")
public class EliminarTrabajoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!AppUtil.isAdmin(request)) { response.sendRedirect(request.getContextPath() + "/login"); return; }
        String id = request.getParameter("id");
        int semana = 1;
        try { semana = Integer.parseInt(request.getParameter("semana")); } catch (Exception ignored) {}
        GridFSBucket bucket = AppUtil.bucket(getServletContext());
        if (bucket != null && id != null && ObjectId.isValid(id)) {
            try { bucket.delete(new ObjectId(id)); }
            catch (Exception ex) { getServletContext().log("Error eliminando archivo.", ex); }
        }
        response.sendRedirect(request.getContextPath() + "/semana?n=" + Math.max(1, Math.min(16, semana)));
    }
}
