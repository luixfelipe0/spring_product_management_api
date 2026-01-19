# Ecommerce API

## Overview

This project is an ecommerce API built with Spring Boot. The idea is to have a simple but well-structured backend that allows managing products, categories, users, and orders. So far, we have implemented most of the basic operations and are gradually evolving.

---

## What has been done

* **Products:** creation, update, search by ID, listing with pagination, and deactivation (soft delete).
* **Categories:** creation and listing.
* **Orders:** creation and search by ID.
* **Validations:** use of Jakarta Validation to ensure consistent data.
* **Exceptions:** handling with custom exceptions for resource not found cases.
* **DTOs and Mappers:** clear separation between entities and transfer objects.
* **Unit tests:** covering the main methods of ProductService (create, update, findById, deactivate).
* **Authentication & Authorization:** implemented user registration and login endpoints with JWT-based authentication.
* **Payment Integration:** integrated Stripe for secure payment processing.

---

## What we are doing now

* Expanding unit test coverage to ensure reliability.
* Adjusting naming details and conventions (e.g., deleteProduct for soft delete).
* Improving endpoint documentation with practical examples.

---

## Next steps

* Implement integration tests with MockMvc to validate endpoints.
* Add more advanced filters in product listing (by category, price range, etc.).
* Better document API usage with Swagger/OpenAPI.

---

## Technologies used

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* JUnit 5 + Mockito
* Swagger/OpenAPI
* H2 Database (for testing)
* Stripe API

---

## Author

Project developed by Luiz Felipe.

---

## API Endpoints

* **User Registration:** POST /auth/register - Allows new users to create an account.
* **User Login:** POST /auth/login - Authenticates users and returns a JWT token.
* **Product Management:** CRUD operations on products.
* **Category Management:** Create and list categories.
* **Order Management:** Create orders and retrieve order details.
* **Payment Processing:** Handles payments securely via Stripe integration.

---

## Authentication

The API uses JWT tokens for authentication. Users must register and then log in to receive a token, which must be included in the Authorization header for protected endpoints.

---

## Payment

Stripe is integrated to handle payments securely. The payment endpoint processes transactions and updates order statuses accordingly.

---

## Error Handling

Custom exceptions are used to handle resource not found and validation errors, returning appropriate HTTP status codes and messages.

---

## Contribution

Contributions are welcome. Please fork the repository and submit pull requests for improvements or bug fixes.