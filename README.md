# Order Processing Service

A simple Order Processing REST API built using Java and Spring Boot.
The application exposes REST endpoints to create and retrieve orders using **in-memory storage**
and secures APIs using **JWT-based authentication and role-based authorization**.

---

## Tech Stack

- Java 8+ (tested with Java 17)
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Maven
- In-memory storage (Map)

---

## Setup and Run Instructions

### Prerequisites
- Java 8 or higher
- Maven

Verify installations:
```bash
java -version
mvn -version
```

---

### Build the Project
```bash
mvn clean install
```

---

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on:
```
http://localhost:9098
```

(Port can be changed in `application.properties`)

---

## Authentication (JWT)

All order APIs are secured using JWT.

There is no database or user management.
A lightweight login endpoint issues JWT tokens for testing and evaluation purposes.

### Login API

**Endpoint**
```
POST /auth/login
```

**Request Body**
```json
{
  "username": "CUST1001",
  "password": "any"
}
```

**Response**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Roles

| Username | Role |
|--------|------|
| admin | ADMIN |
| any other value | USER |

---

## API Usage Examples

All secured APIs require the following header:
```
Authorization: Bearer <JWT_TOKEN>
```

---

### 1. Create Order

**Endpoint**
```
POST /api/orders
```

**Request Body**
```json
{
  "customerId": "CUST1001",
  "product": "Laptop",
  "amount": 75000
}
```

**Response – 201 Created**
```json
{
  "orderId": "b1d1e7f6-9b1c-4a21-9e8f-5d88e7a4d2af",
  "status": "CREATED"
}
```

---

### 2. Get Order by ID

**Endpoint**
```
GET /api/orders/{orderId}
```

**Response – 200 OK**
```json
{
  "orderId": "b1d1e7f6-9b1c-4a21-9e8f-5d88e7a4d2af",
  "customerId": "CUST1001",
  "product": "Laptop",
  "amount": 75000,
  "createdAt": "2025-12-24T15:40:12.123"
}
```

---

### 3. List Orders

**USER**
```
GET /api/orders
```
Returns only logged-in user’s orders.

**ADMIN**
```
GET /api/orders
```
Returns all orders.

**ADMIN (Filtered)**
```
GET /api/orders?customerId=CUST1001
```
Returns orders for the specified customer.

---

## Authorization Rules

| Role | Permissions |
|----|------------|
| ADMIN | Create orders, view all orders |
| USER | Create orders, view only own orders |

---

## Error Handling

| Scenario | HTTP Status |
|--------|-------------|
| Validation failure | 400 Bad Request |
| Unauthorized | 401 Unauthorized |
| Forbidden | 403 Forbidden |
| Order not found | 404 Not Found |

---

## Notes

- Orders are stored in memory and cleared on application restart.
- JWT tokens are valid for 1 hour.
---
