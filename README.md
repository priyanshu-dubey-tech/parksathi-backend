#ParkSathi Backend

Production ready Spring Boot backend for ParkSathi, a QR based smart vehicle communication and parking conflict resolution platform.

This backend enables vehicle owners and affected users to communicate securely in real time using **QR scan workflows, JWT authentication, WebSocket alerts, email OTP verification, complaint evidence upload, and geo-location APIs**.

---

## Features

-  JWT based Authentication & Authorization
-  Email OTP Verification during Registration
-  Multi Vehicle Support per User
-  Unique QR Code Generation for Each Vehicle
-  QR Scan based Vehicle Contact Workflow
-  WebSocket + STOMP Real time Alerts
-  Geo location Support for Complaint Tracking
-  Complaint Image Upload with Local Storage
-  Owner & Victim Session History APIs
-  Smart Parking Conflict Complaint Workflow
-  Redis based Caching for Faster Lookup
-  Secure Environment Variable based Configuration
-  Modular Monolith Backend Architecture

---

## Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Security
- JWT
- Spring WebSocket + STOMP
- Spring Data JPA
- Hibernate
- MySQL
- Redis
- MapStruct
- Lombok
- ZXing (QR Code)
- Spring Mail
- Maven

---

## Project Structure


src/main/java/com/parksathi
│
├── auth
├── vehicle
├── contact
├── complaint
├── notification
├── mapper
├── config
├── security
└── util

---

## Required Environment Variables

Configure these before running:

env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
EMAIL_USERNAME=
EMAIL_PASSWORD=
REDIS_PORT=
FILE_UPLOAD_DIR=
JWT_SECRET=

---

## Core API Modules

- Authentication APIs
- Vehicle Management APIs
- QR Generate / Scan APIs
- Owner Alert APIs
- Complaint Raise APIs
- Image Upload APIs
- Session History APIs
- Location APIs
- WebSocket Notification APIs

---

## Use Case

ParkSathi solves real world parking conflicts where one vehicle blocks another.

Instead of sharing personal phone numbers, users can:

1. Scan vehicle QR
2. Raise secure alert
3. Notify owner instantly
4. Receive response in app
5. Track complaint with image + location evidence

This improves privacy, safety, and faster conflict resolution.

---

## Scalability Highlights

- Redis caching for repeated QR token lookups
- HikariCP connection pooling
- WebSocket real time event architecture
- Environment based secure deployment config
- Modular service layering for future microservice migration

---

## Author

Priyanshu Dubey
Java Backend Developer | Spring Boot | Real time Systems | Scalable API Design
