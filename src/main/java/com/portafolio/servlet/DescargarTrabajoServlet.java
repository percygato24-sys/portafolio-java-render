package com.portafolio.servlet;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.portafolio.config.AppUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.bson.types.ObjectId;
import java.io.IOException;

@WebServlet("/descargar")
public class DescargarTrabajoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        GridFSBucket bucket = AppUtil.bucket(getServletContext());
        if (bucket == null || id == null || !ObjectId.isValid(id)) {
            response.sendError(404); return;
        }
        try {
            GridFSFile file = bucket.find(new org.bson.Document("_id", new ObjectId(id))).first();
            if (file == null) { response.sendError(404); return; }
            Object contentType = file.getMetadata() == null ? null : file.getMetadata().get("contentType");
            response.setContentType(contentType == null ? "application/octet-stream" : contentType.toString());
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" +
                    java.net.URLEncoder.encode(file.getFilename(), java.nio.charset.StandardCharsets.UTF_8).replace("+", "%20"));
            response.setContentLengthLong(file.getLength());
            bucket.downloadToStream(file.getObjectId(), response.getOutputStream());
        } catch (Exception ex) {
            getServletContext().log("Error descargando archivo.", ex);
            if (!response.isCommitted()) response.sendError(500);
        }
    }
}
