package org.example;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        String userName;

        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        } else if (args.length == 2) {
            host = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default 5000.");
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        userName = scanner.nextLine().trim();
        if (userName.isEmpty()) {
            userName = "User" + System.currentTimeMillis();
        }

        ChatClient client = new ChatClient(host, port, userName);
        client.start();
    }
}