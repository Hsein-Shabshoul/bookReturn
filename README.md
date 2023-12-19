# bookReturn - Library Listener Microservice

## Overview

This microservice is designed to work in conjunction with the Library Service, offloading the responsibility of calculating and updating the average book return of each user upon each return. It listens to queues created by the Library Service for returned books and performs the necessary calculations.

## Features

- **Queue Listener:**
  - Listens to RabbitMQ queues for messages about returned books.

- **Calculate Average Return:**
  - Calculates the average book return time for each user.

- **Database Interaction:**
  - Saves the calculated averages back to the database.

## Technologies Used

- **Spring Boot:** Framework for building Java-based enterprise applications.
- **RabbitMQ:** Message broker for handling asynchronous communication.
- **Spring Data JPA:** Simplifies database operations using Java annotations.

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Hsein-Shabshoul/bookReturn.git
