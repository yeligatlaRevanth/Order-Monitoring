# Real-Time Order Monitoring System

A hands-on learning project built with **Java 21 and Spring Boot** to explore event-driven backend development, asynchronous communication, caching, and real-time updates.

This project started as an opportunity to go beyond the usual REST API and CRUD implementations and get practical experience with technologies such as **Apache Kafka, WebSockets, and Redis**.

Rather than focusing on building a full-fledged order management product, the main objective was to understand how these technologies work, why they are used, and how they can be integrated into a Spring Boot application.

## What I Wanted to Learn

I wanted to build something that would give me practical exposure to concepts commonly encountered in modern backend systems.

The project helped me experiment with:

- **Apache Kafka:** Understanding event-driven communication, producers, consumers, topics, and asynchronous processing.
- **WebSockets:** Exploring persistent client-server connections and how servers can push updates to connected clients without requiring repeated API requests.
- **Redis:** Understanding caching and how frequently accessed application data can be served through an in-memory data store.
- **Spring Security & JWT:** Implementing authentication, token validation, and role-based access control.
- **Spring Data JPA:** Working with entity relationships, repositories, and database persistence.
- **Spring Boot Integration:** Bringing these different technologies together in one application.

## How It Works

The application uses a simple order processing scenario to demonstrate how the different components interact.

1. A user authenticates and receives a JWT.
2. The user places an order through a REST API.
3. The application saves the order and publishes an order event to Kafka.
4. A Kafka consumer listens for the event and processes the order lifecycle.
5. As the order status changes, the application broadcasts updates through WebSockets.
6. Connected clients receive these updates in real time.

The idea was to understand the complete flow, from a synchronous REST request to asynchronous event processing and finally real-time client communication.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Primary programming language |
| Spring Boot 3 | Backend application framework |
| Apache Kafka | Event-driven communication |
| WebSockets & STOMP | Real-time communication |
| Redis | Caching |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database interaction |
| H2 Database | Application persistence |
| Gradle | Build automation |
| Docker Compose | Running Kafka and Redis locally |

## Running Locally

### Prerequisites

- Java 21
- Docker
- Docker Compose

### 1. Clone the repository

```bash
git clone https://github.com/yeligatlaRevanth/Order-Monitoring.git
cd Order-Monitoring
```

### 2. Start Kafka and Redis

```bash
docker compose up -d
```

### 3. Run the Spring Boot application

For Linux/macOS:

```bash
./gradlew bootRun
```

For Windows:

```bash
gradlew.bat bootRun
```

The application runs at:

```text
http://localhost:8080
```

The default local setup uses an H2 in-memory database, Kafka on port `9092`, and Redis on port `6379`.

**Note:** The JWT signing secret and demo credentials are intended for local development only. Configure your own secret before using the application in any other environment.

## Project Scope

This is a personal learning and experimentation project, not a production-ready order management system.

The implementation is intentionally kept relatively simple so that the focus remains on understanding how the individual technologies work and how they interact.

There are several areas I would like to improve as I continue learning, including:

- More comprehensive unit and integration testing.
- Better Kafka failure handling and message recovery.
- Reliable database and event consistency.
- More granular WebSocket authentication and authorization.
- More realistic deployment and monitoring setups.

## Final Note

This project represents my attempt to get hands-on with backend concepts beyond basic API development.

Building it gave me a better understanding of asynchronous processing, event-driven communication, caching, and real-time updates, while also helping me identify areas where I need to explore these technologies further.

**It's a learning project, and there's still plenty more to build and improve.**
