# Airline Management System

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen) ![Docker](https://img.shields.io/badge/Docker-Container-blue) ![AWS](https://img.shields.io/badge/AWS-EC2%20%7C%20RDS-orange) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-blueviolet)

A Spring Boot RESTful application for airline management, deployed to AWS EC2 via an automated GitHub Actions CI/CD pipeline. Features include staging/production environments, JWT security, and email notifications.

## Features
- **Automated CI/CD**: GitHub Actions builds, tests, and deploys to staging (`54.179.15.186`) and production (`52.221.220.162`).
- **Containerization**: Dockerized app (`shehan0000/airline-management-system:staging`) ensures consistency across environments.
- **Database**: AWS RDS MySQL (`database-1.cpo2ae66yimr.ap-southeast-1.rds.amazonaws.com`) with JPA schema automation.
- **Security**: JWT authentication for secure API access.
- **Email Notifications**: Integrated Spring Mail for operational alerts.
- **RESTful APIs: Comprehensive endpoints for managing aircraft, airports, bookings, customers, flight routes, staff, and users.
- **Flight Search: Advanced search functionality for flight routes based on origin, destination, passenger count, and flight class.
- **Shortest Route Calculation: Supports finding the shortest flight routes using distance or other weight types.


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
