package org.example;

public class ServerMain {
    public static void main(String[] args) {
        int port = 5000;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        }

        ChatServer server = new ChatServer(port);
        server.start();
    }
}