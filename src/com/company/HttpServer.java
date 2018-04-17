package com.company;

import com.company.Json.JsonFormatter;
import com.company.Json.JsonFormatterImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServer {
    private int port;
    private Map<String, String> paramsMap = new HashMap<>();
    private final String OK_RESPONSE = "HTTP/1.1 200 OK\r\n\r\n";
    private final String CREATE_USER = "user/create?";
    private final String DELETE_USER = "user/delete/";
    private final String GET_USER_JSON = "user/";
    private final String GET_LIST_JSON = "user/list";
    private final String NOT_FOUND = "HTTP/1.1 404 Not Found";
    private Serialization serialization = new Serialization("D:\\Soft\\Temp");

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        while (true) {
            listenPort();
        }
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
            String response = getAnswerByRequest(result);
            clientSocket.getOutputStream().write(response.getBytes("UTF-8"));
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseParams(String inputRequest) {
        int startPos = 0;
        int endPos;
        int splitterPos;
        String parameter;
        String value;
        while (startPos < inputRequest.length()) {
            endPos = inputRequest.indexOf('&', startPos);
            if (endPos == -1) {
                endPos = inputRequest.length();
            }
            splitterPos = inputRequest.indexOf("=", startPos);
            parameter = inputRequest.substring(startPos, splitterPos);
            value = inputRequest.substring(splitterPos + 1, endPos);
            paramsMap.put(parameter, value);
            startPos = endPos + 1;
        }
    }

    private String getAnswerByRequest(String request) {
        int endPos = request.indexOf("HTTP/") - 1;
        String paramsString = request.substring(5, endPos);

        if (paramsString.startsWith(CREATE_USER)) {
            parseParams(paramsString.substring(CREATE_USER.length()));
            User user = new User(paramsMap.get("name"), Integer.parseInt(paramsMap.get("age")), Integer.parseInt(paramsMap.get("salary")));
            return OK_RESPONSE + String.valueOf(serialization.serializeObject(user));
        }
        if (paramsString.startsWith(DELETE_USER)) {
            int userId = Integer.parseInt(paramsString.substring(DELETE_USER.length()));
            if (serialization.deleteUser(userId)) {
                return OK_RESPONSE + "OK";
            }
            return NOT_FOUND;
        }
        if (paramsString.startsWith(GET_LIST_JSON)) {
            List<User> users = serialization.getUsersArray();
            JsonFormatter formatter = new JsonFormatterImpl();
            String result = "";
            for (User elem : users) {
                result += formatter.marshall(elem) + "\n\n";
            }
            return OK_RESPONSE + result;
        }
        if (paramsString.startsWith(GET_USER_JSON)) {
            int userId = Integer.parseInt(paramsString.substring(GET_USER_JSON.length()));
            User user = serialization.deserializeObject(userId);
            if (user == null) {
                return NOT_FOUND;
            }
            JsonFormatter formatter = new JsonFormatterImpl();
            return OK_RESPONSE + formatter.marshall(user);
        }

        return "Wrong request";
    }
}
