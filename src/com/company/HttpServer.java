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
    private final String OK_RESPONSE = "HTTP/1.1 200 OK\r\n\r\n";

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        listenPort();
    }

    private void listenPort() {
        waitRequestAndSendAnswer();
    }

    private void waitRequestAndSendAnswer() {
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
            String responce = OK_RESPONSE + getAnswerByRequest(result);
            clientSocket.getOutputStream().write(responce.getBytes("UTF-8"));
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String getAnswerByRequest(String request) {
        parseParams(request);
        return String.valueOf(Integer.parseInt(params.get("a")) + Integer.parseInt(params.get("b")));
    }
}
