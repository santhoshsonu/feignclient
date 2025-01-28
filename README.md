Spring Boot Feign Client with Retry and Circuit Breaker
===========================================================

Description
------------

This project demonstrates the use of Spring Boot Feign client with retry and circuit breaker patterns. The project
showcases how to implement resilient communication between microservices using Feign client, Retryer, and Circuit
Breaker.

Features
------------

* Spring Boot Feign client for making HTTP requests to a remote service
* Retry mechanism using Resilience4j to handle temporary failures
* Circuit Breaker pattern using Resilience4j to prevent cascading failures
* Prometheus and Jaeger for monitoring and tracing

Technologies Used
--------------------

* Java 17
* Spring Boot 3.x
* Spring Cloud Feign
* Resilience4j
* Micrometer


Getting Started
---------------

### Installation

1. Clone the repository: `git clone https://github.com/santhoshsonu/feignclient.git`
2. Build the project: `mvn clean install`
3. Run the application (optional: jaeger and prometheus to be running): `java -jar target/feignclient.jar`

### Running the Project

1. Start the application
2. Use a tool like Postman or cURL to make requests to the Feign client endpoint (
   e.g., `http://localhost:8080/api/v1/sample`)
3. Observe the retry and circuit breaker behavior in the application logs


### Docker Compose

To run the project using Docker Compose, follow these steps:

1. Compile the project: 
   `mvn clean package`
2. Build the Docker image from within the project directory: 
   `docker build -t your-image-name -f src\main\docker\Dockerfile .`
3. Run the Docker compose file from within the project directory: 
   `docker compose -f deployment\docker-compose\compose.yml up`
