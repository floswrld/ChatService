# ChatService

Create Java App:
```bash
mvn clean package
```

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