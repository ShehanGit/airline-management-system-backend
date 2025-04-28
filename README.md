# Airline Management System

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen) ![Docker](https://img.shields.io/badge/Docker-Container-blue) ![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20RDS-orange) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-blueviolet)

A Spring Boot RESTful application for airline management, deployed to AWS EC2 via an automated GitHub Actions CI/CD pipeline. Features include staging/production environments, JWT security, and email notifications.

## Features
- **Automated CI/CD**: GitHub Actions builds, tests, and deploys to staging (`54.179.15.186`) and production (`52.221.220.162`).
- **Containerization**: Dockerized app (`shehan0000/airline-management-system:staging`) ensures consistency across environments.
- **Database**: AWS RDS MySQL (`database-1.cpo2ae66yimr.ap-southeast-1.rds.amazonaws.com`) with JPA schema automation.
- **Security**: JWT authentication for secure API access.
- **Email Notifications**: Integrated Spring Mail for operational alerts.
- RESTful APIs: Comprehensive endpoints for managing aircraft, airports, bookings, customers, flight routes, staff, and users.
- Flight Search: Advanced search functionality for flight routes based on origin, destination, passenger count, and flight class.
- Shortest Route Calculation: Supports finding the shortest flight routes using distance or other weight types.


## Prerequisites
- AWS account (EC2, RDS)
- Docker installed
- GitHub account with Secrets configured
- Java 17, Maven

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/shehan0000/airline-management-system.git
   cd airline-management-system
   ```
2. **Clone the Repository**:
   - Set up AWS credentials and GitHub Secrets for DB_URL, DB_USERNAME, and DB_PASSWORD.
   - Update application.properties with your RDS MySQL connection details if not using environment variables:
   ```bash
   spring.datasource.url=jdbc:mysql://<your-rds-endpoint>:3306/airline_management?useSSL=false
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```
3. **Run Locally:**:
   ```bash
   mvn clean install
   ```
3. **Build the Application:**:
   ```bash
   mvn spring-boot:run
   ```
4. Dockerize the Application:
   ```bash
   docker build -t shehan0000/airline-management-system:staging .
   ```
   ```bash
   docker run -p 8080:8080 shehan0000/airline-management-system:staging
   ```
5. Deploy to AWS:
   - Ensure GitHub Actions workflow is configured in .github/workflows/ci-cd.yml.
   - Push changes to the main branch for staging deployment or tag a release for production.
  
# Algorithm Details: Dijkstra's Algorithm
### The application implements Dijkstra's Algorithm in the DijkstraAlgorithm class to compute the shortest flight routes between airports, optimizing based on user-specified criteria (distance, cost, or time). This enhances the flight route planning and search functionality.

## How It Works

- ### Graph Representation:

   - Airports are nodes, and flight routes are directed edges stored in an adjacency list.

Each edge has weights for distance, cost, and time, allowing flexible optimization.

The graph is built from the FlightRoute entities retrieved from the database.

### Algorithm Execution:

Initialization: Sets the source airport's distance to 0 and others to infinity, using a priority queue to track nodes with the smallest tentative distances.

Weight Selection: Supports three weight types (DISTANCE, COST, TIME) via the WeightType enum, allowing the algorithm to optimize based on the user's preference.

Path Computation: Iteratively selects the node with the smallest distance, updates distances to neighbors, and tracks the previous node for path reconstruction.

Path Reconstruction: Builds the shortest path by backtracking from the destination to the source using the previous node map.

### Implementation Details:

Uses a PriorityQueue for efficient node selection.

Handles unidirectional routes (no reverse edges).



Returns a list of Airport objects representing the shortest path.

## Benefits


Optimized Route Planning: Enables passengers to find the most efficient routes based on distance (shortest path), cost (cheapest path), or time (fastest path).

Enhanced User Experience: The flight search API (/api/routes/shortest) leverages this algorithm to provide accurate and optimized route suggestions.

Operational Efficiency: Helps airline staff plan cost-effective and timely flight schedules.

Flexibility: Supports multiple optimization criteria, making it adaptable to various use cases (e.g., budget travelers vs. time-sensitive business travelers).

Scalability: Efficiently handles large airport networks due to the use of a priority queue and adjacency list.

## Project Structure
   ```bash
   airline-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/airlinemanagementsystem/airline_management_system/
│   │   │   ├── controller/     
│   │   │   ├── model/          
│   │   │   ├── service/        
│   │   │   ├── repository/     
│   │   │   ├── user/           
│   │   │   ├── exception/    
│   │   ├── resources/
│   │       ├── application.properties  # Configuration file
│   ├── test/                  
├── Dockerfile                  
├── pom.xml                      
├── .github/workflows/ci-cd.yml  
└── README.md                    
   ```

## Database Configuration
 ```bash
   spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/airline_management?useSSL=false}
   spring.datasource.username=${DB_USERNAME:root}
   spring.datasource.password=${DB_PASSWORD:user}
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   spring.jpa.hibernate.ddl-auto=update
   server.port=8080
 ```

## Security
- JWT Authentication: APIs are secured using JSON Web Tokens. Include the Authorization header with Bearer <token> for protected endpoints.
- CORS: Cross-Origin Resource Sharing is enabled for specific endpoints (e.g., /customers, /api/routes/search).

## CI/CD Pipeline
- The GitHub Actions workflow automates:
- Building and testing the application.
- Creating and pushing Docker images to Docker Hub.
- Deploying to AWS EC2 instances for staging (54.179.15.186) and production (52.221.220.162).
  Configure the workflow in .github/workflows/ci-cd.yml with appropriate AWS credentials and Docker Hub secrets.

## License
### This project is licensed under the MIT License.
