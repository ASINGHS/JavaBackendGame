package com.king.backend.httpserver;

import com.king.backend.manager.GameManager;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Main Class where the HttpServer for the BackEnd is deployed.

public class BackEndServer {

    public static int PORT = 8081;

    public static void main(String[] args) throws Exception {
       
        try {
            System.out.println("\n\n   Starting HTTPServer.");
            String hostName = "localhost";
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            HttpContext httpContext = httpServer.createContext("/", new BackEndHttpHandler(GameManager.getInstance()));
            httpContext.getFilters().add(new BackEndHttpFilter());
            ExecutorService executorService = Executors.newCachedThreadPool();
            httpServer.setExecutor(executorService);
            httpServer.start();
            System.out.println("   HTTPServer started in http://" + hostName + ":" + PORT + "/");
            System.out.println("   Started HTTPServer Successfully!\n");
        } catch (Exception e) {
            System.err.println("Error with the HTTPServer.");
            System.err.println(e.getMessage());

        }
    }
}
