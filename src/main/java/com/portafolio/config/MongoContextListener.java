package com.portafolio.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MongoContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        String uri = System.getenv("MONGODB_URI");
        String databaseName = System.getenv().getOrDefault("MONGODB_DATABASE", "portafolio_universitario");
        if (uri == null || uri.isBlank()) {
            event.getServletContext().log("MONGODB_URI no está configurada. Configure la variable antes de iniciar la aplicación.");
            return;
        }
        try {
            MongoClient client = MongoClients.create(uri);
            MongoDatabase database = client.getDatabase(databaseName);
            GridFSBucket bucket = GridFSBuckets.create(database, "trabajos");
            event.getServletContext().setAttribute("mongoClient", client);
            event.getServletContext().setAttribute("mongoDatabase", database);
            event.getServletContext().setAttribute("gridFSBucket", bucket);
            event.getServletContext().log("Conexión con MongoDB inicializada.");
        } catch (Exception ex) {
            event.getServletContext().log("No se pudo inicializar MongoDB.", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        MongoClient client = (MongoClient) event.getServletContext().getAttribute("mongoClient");
        if (client != null) client.close();
    }
}
