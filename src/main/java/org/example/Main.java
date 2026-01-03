package org.example;

import java.util.Scanner;

/**
 * Starts server or client according to the commandline-argument
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String mode = args[0].toLowerCase();

        switch (mode) {
            case "server" -> startServer(args);
            case "client" -> startClient(args);
            default -> {
                System.out.println("Unknown mode: " + mode);
                printUsage();
            }
        }
    }

    private static void startServer(String[] args) {
        int port = 5001;
        if (args.length == 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        } else {
            printUsage();
            return;
        }
        ChatServer server = new ChatServer(port);
        server.start();
    }

    private static void startClient(String[] args) {
        String host = "localhost";
        int port = 5001;

        if (args.length == 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        } else if (args.length == 3) {
            host = args[1];
            try {
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        } else {
            printUsage();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String userName = scanner.nextLine().trim();
        if (userName.isEmpty()) {
            userName = "User" + System.currentTimeMillis();
        }

        ChatClient client = new ChatClient(host, port, userName);
        client.start();
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java org.example.Main server <port>");
        System.out.println("  java org.example.Main client <host> <port>");
    }
}