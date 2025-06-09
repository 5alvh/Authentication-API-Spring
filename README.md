# Authentication API

## Overview

This repository implements a RESTful API for user authentication, including **login**, **logout**, and **signup** functionality. It is built with Spring Boot and uses JWT (JSON Web Token) for secure stateless authentication. 

Key features:
- User registration and authentication.
- Role-based access control (Admin, Customer).
- JWT-based security.
- CORS handling for cross-origin requests.
- Modular and extensible design for enterprise applications.

---

## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [Architecture](#architecture)

---

## Technologies Used

- **Java** (JDK 11+)
- **Spring Boot** (v2.7+)
- **Spring Security** for authentication and authorization.
- **JWT** for token-based security.
- **Hibernate** as ORM.
- **MYSQL** for local development.
- **Maven** for dependency management.

---

## Features

1. **Signup**: Create a new user account with secure password encryption.
2. **Login**: Authenticate using email and password, with JWT token issuance.
3. **Role-Based Access Control**: Endpoint permissions for different user roles (Admin, Customer).
4. **CORS Support**: Configured for frontend and external integrations.
5. **Scalable Design**: Easily extendable for additional roles and features.

---

## Setup

### Prerequisites

1. Install **Java JDK 11+**.
2. Install **Maven** for dependency management.
3. A database such as **H2**, **MySQL**, or **PostgreSQL**.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/5alvh/Authentication-API-Spring-Boot.git
   cd auth-api
2. Build the project:
   ```bash
   mvn clean install
3. Run the application:
   ```bash
   mvn spring-boot:run
4. Access the API documentation at http://localhost:8080/swagger-ui.html (if Swagger is configured) or use tools like Postman for API testing.

   ---

## API Endpoints
### Authentication
- **POST /api/auth/signup**
- Description: Register a new user.
- Request Body:
   ```bash
   {
     "name": "Salah Eddine Khouadri",
     "email": "test@example.com",
     "password": "test"
   }
- **POST /api/auth/login**
- Description: Authenticate an existing user.
- Request Body:
   ```bash
   {
  "email": "test@example.com",
  "password": "testtest"
   }
### Authentication
- Admin: /api/admin/** (Accessible to users with Admin role only).
- Customer: /api/customer/** (Accessible to users with Customer role only).

    ---
  
## Architecture
### Key Components

1. Controller:
- **AuthController** handles authentication logic (signup/login).

2. Service:
- **AuthService** for user-related operations.
- **UserService** for Spring Security integration.
  
3. Security:

- **WebSecurityConfiguration** for security filters and role-based access.
- **JwtAuthenticationFilter** for validating JWT tokens.
- 
4. Persistence:
- **UserRepository** for database operations.

```bash
   [Client] --> [AuthController] --> [AuthService] --> [UserRepository]
        <-- JWT Token
