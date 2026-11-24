package com.portafolio.config;

import com.mongodb.client.gridfs.GridFSBucket;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class AppUtil {
    private AppUtil() {}

    public static GridFSBucket bucket(ServletContext context) {
        return (GridFSBucket) context.getAttribute("gridFSBucket");
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute("admin"));
    }

    public static void requireAdmin(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws java.io.IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
