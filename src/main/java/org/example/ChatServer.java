package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the connections to the chatClients
 * Broadcasts messages to all clients
 */
public class ChatServer {

    private final int port;
    private final Set<ClientHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("ChatServer starting on port " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("ChatServer started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler handler = new ClientHandler(this, clientSocket);
                clients.add(handler);

                // Start the handler in a separate thread so the server can immediately
                // go back to waiting for the next client (non-blocking)
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in ChatServer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        // Synchronize on the 'clients' set to prevent exceptions
        // if a client disconnects while we are iterating here.
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public void removeClient(ClientHandler handler) {
        clients.remove(handler);
        System.out.println("Client removed: " + handler.getClientName());
    }
}
