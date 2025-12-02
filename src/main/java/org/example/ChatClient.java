package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private final String host;
    private final int port;
    private final String userName;

    public ChatClient(String host, int port, String userName) {
        this.host = host;
        this.port = port;
        this.userName = userName;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to chat server " + host + ":" + port);

            Thread listener = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            listener.setDaemon(true);
            listener.start();
            out.println(userName);

            while (true) {
                String line = scanner.nextLine();
                out.println(line);
                if ("/quit".equalsIgnoreCase(line.trim())) {
                    break;
                }
            }

            System.out.println("Exiting chat...");

        } catch (IOException e) {
            System.err.println("Error in ChatClient: " + e.getMessage());
            e.printStackTrace();
        }
    }
}