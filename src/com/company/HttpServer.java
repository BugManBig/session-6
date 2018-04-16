package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private int port;
    private Map<String, String> params = new HashMap<>();

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        listenPort();
    }

    private void listenPort() {
        String resultOfRequest = getInputRequest();
        parseParams(resultOfRequest);
    }

    private String getInputRequest() {
        String result = "";
        String line;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            line = reader.readLine();
            while (!line.isEmpty()) {
                result += line + "\n";
                line = reader.readLine();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void parseParams(String inputRequest) {
        int startPos = 0;
        int endPos = inputRequest.indexOf("HTTP/") - 1;
        String paramsString = inputRequest.substring(5, endPos);
        int splitterPos;
        String parameter;
        String value;
        while (startPos < paramsString.length()) {
            endPos = paramsString.indexOf('&', startPos);
            if (endPos == -1) {
                endPos = paramsString.length();
            }
            splitterPos = paramsString.indexOf("=", startPos);
            parameter = paramsString.substring(startPos, splitterPos);
            value = paramsString.substring(splitterPos + 1, endPos);
            params.put(parameter, value);
            startPos = endPos + 1;
        }
    }
}
