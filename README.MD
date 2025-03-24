# Online Book Store

A Book store application built with Spring Boot and Java.

## Features

- **Book Inventory Management:**  
  Manage books with validation and search functionality by title, author, year, and genre.
- **Shopping Cart Operations:**  
  Add, remove, and clear books in the user's cart.
- **Checkout Process:**  
  Process checkouts with multiple payment options (Web, USSD, Transfer).
- **Purchase History Tracking:**  
  View past orders and detailed order information.

## Technologies & Dependencies

- **Java 21 (LTS)**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **H2 Database** (for development)
- **JUnit & Mockito** (for testing)
- **Maven**
- **Lombok**

## Getting Started

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Ebukachuqz/Springboot-Online-Book-Store
   cd Springboot-Online-Book-Store
   ```

2. **Build the application:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
    - **API Endpoints:**  
      [http://localhost:8080/api/](http://localhost:8080/api/)
    - **H2 Console:**  
      [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
        - **JDBC URL:** `jdbc:h2:mem:bookstore`
        - **Username:** `sa`
        - **Password:** *(leave empty)*

## Configuration

The application is configured via the `application.properties` file in `src/main/resources`:

```properties
spring.application.name=Online Book Store
server.port=8080
spring.datasource.url=jdbc:h2:mem:bookstore
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Additionally, a `data.sql` file is included in the resources folder to populate initial sample data (books, users, carts) when the application starts.

## API Endpoints

### Books
- **Get all books:**  
  `GET /api/books`
- **Get book by ID:**  
  `GET /api/books/{id}`
- **Search books:**  
  `GET /api/books/search`  
  Optional query parameters: `title`, `author`, `year`, `genre`

### Cart
- **Get user's cart:**  
  `GET /api/cart/{userId}`
- **Add book to cart:**  
  `POST /api/cart/{userId}/items`  
  Parameters: `bookId`, `quantity` (default is 1)
- **Remove book from cart:**  
  `DELETE /api/cart/{userId}/items/{bookId}`
- **Clear cart:**  
  `DELETE /api/cart/{userId}`

### Checkout & Orders
- **Process checkout:**  
  `POST /api/checkout/{userId}`  
  Parameter: `paymentMethod` (values: WEB, USSD, TRANSFER)
- **Get user's order history:**  
  `GET /api/orders/{userId}`
- **Get specific order details:**  
  `GET /api/orders/{userId}/{orderId}`

### Users
- **Create user:**  
  `POST /api/users/create`  
  Request Body (JSON):
   ```json
   {
     "username": "ebukachuqz"
   }
   ```
- **Get user by username:**  
  `GET /api/users/{username}`
- **Get user by ID:**  
  `GET /api/users/id/{id}`

## Testing Endpoints with Postman

You can test the API endpoints using Postman. Below are some example requests and payloads:

### 1. Get All Books
- **Method:** GET
- **URL:** `http://localhost:8080/api/books`

### 2. Get a Book by ID
- **Method:** GET
- **URL:** `http://localhost:8080/api/books/1`

### 3. Search Books
- **Method:** GET
- **URL:** `http://localhost:8080/api/books/search?title=Great&author=John&year=2020&genre=FICTION`

### 4. Get User Cart
- **Method:** GET
- **URL:** `http://localhost:8080/api/cart/1`

### 5. Add Book to Cart
- **Method:** POST
- **URL:** `http://localhost:8080/api/cart/1/items`
- **Query Parameters:**
    - `bookId`: 2
    - `quantity`: 1

### 6. Remove Book from Cart
- **Method:** DELETE
- **URL:** `http://localhost:8080/api/cart/1/items/2`

### 7. Clear Cart
- **Method:** DELETE
- **URL:** `http://localhost:8080/api/cart/1`

### 8. Checkout
- **Method:** POST
- **URL:** `http://localhost:8080/api/checkout/1`
- **Query Parameter:**
    - `paymentMethod`: WEB
- **Example URL:**  
  `http://localhost:8080/api/checkout/1?paymentMethod=WEB`

### 9. Get User Order History
- **Method:** GET
- **URL:** `http://localhost:8080/api/orders/1`

### 10. Get Specific Order Details
- **Method:** GET
- **URL:** `http://localhost:8080/api/orders/1/1`

### 11. Create User
- **Method:** POST
- **URL:** `http://localhost:8080/api/users/create`
- **Request Body (JSON):**
   ```json
   {
     "username": "ebukachuqz"
   }
   ```

### 12. Get User by Username
- **Method:** GET
- **URL:** `http://localhost:8080/api/users/ebukachuqz`

### 13. Get User by ID
- **Method:** GET
- **URL:** `http://localhost:8080/api/users/id/1`

## Running Tests

Run the test suite with:
```bash
mvn test
```

This suite includes unit tests for controllers, services, and repository integration tests.

