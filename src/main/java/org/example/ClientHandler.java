package org.example;

import java.io.*;
import java.net.Socket;

/**
 * Handles the communication lifecycle for a single connected client.
 * Implements Runnable to execute in a separate thread, ensuring the server
 * remains non-blocking while handling multiple clients.
 */
public class ClientHandler implements Runnable {

    private final ChatServer server;
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName = "Anonymous";

    public ClientHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String name = in.readLine();
            if (name != null && !name.isBlank()) {
                clientName = name.trim();
            }
            out.println("Welcome, " + clientName + "! Type '/quit' to exit.");
            server.broadcast("[System] " + clientName + " has joined the chat.", this);

            String line;
            while ((line = in.readLine()) != null) {
                if ("/quit".equalsIgnoreCase(line.trim())) {
                    break;
                }

                String formatted = clientName + ": " + line;
                System.out.println("Received from " + clientName + ": " + line);
                server.broadcast(formatted, this);
            }
        } catch (IOException e) {
            System.err.println("Connection error with client " + clientName + ": " + e.getMessage());
        } finally {
            close();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void close() {
        try {
            server.broadcast("[System] " + clientName + " has left the chat.", this);
            server.removeClient(this);
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection for " + clientName + ": " + e.getMessage());
        }
    }
}