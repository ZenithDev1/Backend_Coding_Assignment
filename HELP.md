# Ticket Booking System Backend - EVA Bharat Coding Assignment

A lightweight, secure, and production-ready Spring Boot REST API implementation for a Ticket Management System, complete with JWT Authentication, Role-less Ownership Authorization, and optimized multi-stage Docker configuration. This project satisfies all constraints specified in the EVA Bharat Backend Intern Assignment.

---

## Deployment & Document Links
* **GitHub Repository:** [https://github.com/ZenithDev1/Backend_Coding_Assignment.git](https://github.com/ZenithDev1/Backend_Coding_Assignment.git)
* **EVA Bharat Assignment PDF (Google Drive):** [https://drive.google.com/file/d/10Dcqm7_RSg25a6Ao5Av31Y1mBi3PJxQV/view?usp=drivesdk](https://drive.google.com/file/d/10Dcqm7_RSg25a6Ao5Av31Y1mBi3PJxQV/view?usp=drivesdk)
* **Deployed Application URL (AWS / Cloud):** `https://ticket-system-backend.onrender.com`
* **Public Health Check URL:** `https://ticket-system-backend.onrender.com/health`

---

## Features & Implementation Details

* **Language/Framework:** Java 21 & Spring Boot 3.3.4 (Optimized Over Go contract).
* **Authentication:** Stateless Security utilizing JSON Web Tokens (JWT).
* **Data Persistence:** Relational MySQL Database with automated schema binding.
* **State Machine Validation:** Strict lifecycle constraint checking (`open` ➡️ `in_progress` ➡️ `closed`). 
* Closed tickets are strictly immutable and cannot be reverted.

---

# How to RUN
Agar aapko locally source code build nahi karna hai, toh aap direct mere public **Docker Hub** registry se Docker image pull karke run kar sakte hain, iske liye niche diye gaye steps ko dhyan se follow karein:

#### **Step 1: Create Database**
First of all you have to create a Mysql datbase on local system with CMD given below:
```bash
CREATE DATABASE ticket_booking_db;
```


#### **Step 2: Pull the Image from Docker Hub**
Then Docker terminal par niche di gayi command chalakar container image ko pull karein:
```bash
docker pull zenithdev1/ticket-system:latest
```

#### **Step 3: RUN the container**
Use the cmd given below to run the application through the DockerDesktop
```bash
docker run -p 8080:8080 -e DB_HOST=host.docker.internal -e DB_PORT=3306 -e DB_NAME=ticket_booking_db -e DB_USER=root -e DB_PASSWORD=root zenithdev1/ticket-system:latest
```

#### **Step 3: Health check of the application**
hit the given **GET** endpoint in your postman to get health status of the application:
```bash
http://localhost:8080/health
```

#### **Step 4: Result and Successful Execution of the Application**
After completing all the steps in correct order you will recieve this output on your POSTMAN:
````bash
{
  "status": "ok"
}
````
---

### Verification Status
As shown in **Step 4** above, when you hit the `/health` endpoint, you will receive the verified JSON response: `{"status": "ok"}`.

> ##  CONGRATULATIONS! YOU NAILED IT!