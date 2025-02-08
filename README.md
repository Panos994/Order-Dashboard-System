Order Details Dashboard System

Description

This is a simple Order Details Dashboard System that showcasing order details to a User (Client). Each User can place his orders from the available products and when he has placed the order, then it is  displayed the order’s id, product’s name, quantity, total cost and a timestamp of when the order has been received.


To be more specific, it contains a User Login for authentication through Spring Security, a Dashboard that displays order details. Orders are stored in PostgreSQL and managed via Hibernate. I am using a role-based access model where I am using 2 roles with different levels of access. There is an ADMIN role that adds products with their details (product id, name, cost, quantity) and Users which are clients that can placed their orders and showcase order details. 







Source Code

Github Repository: https://github.com/Panos994/Order-Dashboard-System.git 








Requirements

In order to install and run this system you need Java 17+, Maven 3+, PostgreSQL 13+ for the backend. 

Also, node.js 16+ and Vue.js3 for the for the frontend. 






Installation and Execution Instructions 

In order to install the system we can clone with git the repository and go to the system’s folder :

git clone https://github.com/Panos994/Order-Dashboard-System.git
cd QNR_OrderSystem

In order to configure postgreSQL we need to create the database and the user: 

sudo -i -u postgres
psql
CREATE DATABASE order_system_db;
CREATE USER dbuser WITH ENCRYPTED PASSWORD 'pass123';
GRANT ALL PRIVILEGES ON DATABASE order_system_db TO dbuser;
\q

(On a Windows OS, you can execute the same commands in a psql shell or a postgreSQL Client)


Then we can run our Spring boot backend with: 
mvn spring-boot:run


To run it locally: 
The server starts in http://localhost:9090


For the Frontend

We need to go to the frontend’s folder:
cd order_system_folder

Then we will install all necessary dependencies:
npm install

And run the frontend app:
npm run serve


To run it locally: 
the server starts at http://localhost:8080 









Database Schema

Database schema is automatically created by Hibernate. However, below I am including sample data for orders and users.
Sample Users Insertions:
INSERT INTO users (username, password, role) VALUES ('Admin', 'pass1234', 'ROLE_ADMIN'); 
INSERT INTO users (username, password, role) VALUES ('Client1', '1234pass', 'ROLE_USER');







Sample Products Insertions:
INSERT INTO products (name, cost, quantity) VALUES ('CPUs ', 220, 50); 
INSERT INTO products (name, cost, quantity) VALUES ('RAMs', 90, 30);


Sample Orders Insertions: 
INSERT INTO orders (user_id, product_id, quantity, total_cost, create_time) VALUES (1, 1, 2, 200.00, NOW()); 

INSERT INTO orders (user_id, product_id, quantity, total_cost, create_time) VALUES (2, 2, 1, 200.00, NOW());
