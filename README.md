# Java CLI ChatService

Console-based chat application built with Java Sockets. This project allows multiple clients to communicate in real-time via a central server.

## Features

* **Client-Server Architecture:** A central server manages multiple concurrent client connections.
* **Multithreading:** The server handles each connected client in a separate thread to ensure non-blocking communication.
* **Broadcasting:** Messages sent by one client are automatically relayed to all other connected clients.
* **Usernames:** Clients can choose a custom username upon joining.
* **System Notifications:** Automatic alerts when users join or leave the chat.

## Project Structure

1.  **`Main.java`**
    * The entry point of the application.
    * Determines whether to start as **Server** or **Client** based on command-line arguments.

2.  **`ChatServer.java`**
    * Initializes the `ServerSocket` on a specified port.
    * Maintains a thread-safe list of active client handlers.
    * Accepts incoming connections and spawns a new thread for each client.

3.  **`ClientHandler.java`**
    * Runs as a `Runnable` on the server side.
    * Handles input/output for a specific client connection.
    * Reads incoming messages and triggers the server's broadcast method.

4.  **`ChatClient.java`**
    * Connects to the server and handles the user session.
    * Uses a separate **daemon thread** to listen for incoming messages from the server asynchronously while the main thread waits for user input.


## ChatService Setup

[OPTIONAL] Recreate Java App:
`mvn clean package`

Build Docker Image: 
```bash 
docker compose build
```

Start Server:
```bash
docker compose up -d server
```

Start Client (Please each client in a new terminal):
```bash
docker compose run --rm client
```
