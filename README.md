📦 Order Service – README
📝 Overview

The Order Service is a Spring Boot microservice responsible for:

Creating customer orders

Storing order items

Fetching order details

Managing order status

Connecting with other microservices (User Service, Menu Service, Payment Service in the future)

The service uses PostgreSQL as the database.

⚙️ Tech Stack

Java 23

Spring Boot 3+

Spring Data JPA

Spring Web

PostgreSQL

Lombok

Hibernate

📂 Project Structure
order-service/
 ├── src/main/java/com/nomanis/order
 │    ├── controller
 │    │     └── OrderController.java
 │    ├── dto
 │    │     └── OrderRequest.java
 │    │     └── OrderResponse.java
 │    ├── entity
 │    │     └── Order.java
 │    │     └── OrderItem.java
 │    ├── repository
 │    │     └── OrderRepository.java
 │    │     └── OrderItemRepository.java
 │    ├── service
 │    │     └── OrderService.java
 │    └── OrderServiceApplication.java
 ├── src/main/resources/
 │    └── application.properties
 └── pom.xml

🛠️ Configuration – application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8083

🧪 Sample Order Data (JSON)
Sample Order Request
{
  "customerId": 101,
  "totalAmount": 650.00,
  "orderItems": [
    {
      "itemName": "Chicken Kabab Roll",
      "quantity": 2,
      "price": 120
    },
    {
      "itemName": "Mutton Seekh Kabab",
      "quantity": 1,
      "price": 200
    },
    {
      "itemName": "Cold Drink",
      "quantity": 2,
      "price": 50
    }
  ]
}

📤 Sample Order Response
{
  "orderId": 501,
  "customerId": 101,
  "totalAmount": 650,
  "status": "CONFIRMED",
  "orderDate": "2025-01-18T18:23:12",
  "items": [
    {
      "itemName": "Chicken Kabab Roll",
      "quantity": 2,
      "price": 120
    },
    {
      "itemName": "Mutton Seekh Kabab",
      "quantity": 1,
      "price": 200
    },
    {
      "itemName": "Cold Drink",
      "quantity": 2,
      "price": 50
    }
  ]
}

🔗 API Endpoints
➕ Create Order

POST /api/orders

{
  "customerId": 101,
  "totalAmount": 650,
  "orderItems": [
    {
      "itemName": "Chicken Kabab Roll",
      "quantity": 2,
      "price": 120
    }
  ]
}

📄 Get Order by ID

GET /api/orders/{orderId}

👤 Get Orders by Customer ID

GET /api/orders/customer/101

🔁 Update Order Status

PUT /api/orders/{orderId}/status?value=DELIVERED

❌ Delete Order

DELETE /api/orders/{orderId}

🗄️ PostgreSQL Sample Table Data
order table
order_id	customer_id	total_amount	status	order_date
501	101	650.00	CONFIRMED	2025-01-18 18:23:12
order_item table
item_id	order_id	item_name	quantity	price
1	501	Chicken Kabab Roll	2	120
2	501	Mutton Seekh Kabab	1	200
3	501	Cold Drink	2	50
▶️ How to Run
1️⃣ Start PostgreSQL & create DB:
CREATE DATABASE orderdb;

2️⃣ Run:
mvn spring-boot:run

3️⃣ Test in Postman using sample JSON.

#Swagger UI Links

http://localhost:8083/swagger-ui/index.html#/order-controller/updateOrder
