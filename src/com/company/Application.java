package com.company;

public class Application {
    public void run() {
        HttpServer server = new HttpServer(80);
        server.start();
    }
}
