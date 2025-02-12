# Order Details Dashboard System

## Description
This is a simple **Order Details Dashboard System** that showcases order details to a **User (Client)**. Each user can place orders from the available products, and once an order is placed, the system displays:
- **Order ID**
- **Product Name**
- **Quantity**
- **Total Cost**
- **Timestamp** of when the order was received

The system includes:
- **User authentication** via Spring Security
- **Dashboard** displaying order details
- **PostgreSQL** as the database, managed via Hibernate
- **Role-based access control:**
  - **ADMIN** can add products with details (Product ID, Name, Cost, Quantity)
  - **Users (Clients)** can place orders and view order details

---

## Source Code
**GitHub Repository:** [Order Dashboard System](https://github.com/Panos994/Order-Dashboard-System.git)

---

## Requirements
To install and run this system, ensure you have:
- **Java 17+**
- **Maven 3+**
- **PostgreSQL 13+** (Backend)
- **Node.js 16+** and **Vue.js 3** (Frontend)

---

## Installation and Execution Instructions

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/Panos994/Order-Dashboard-System.git
cd Order-Dashboard-System
```

### 2️⃣ Install PostgreSQL
#### 🔹 **Linux (Ubuntu/Debian-based)**
```sh
sudo apt update
sudo apt install postgresql postgresql-contrib
```

#### 🔹 **Windows**
1. Download and install PostgreSQL from: [PostgreSQL Official Site](https://www.postgresql.org/download/windows/)
2. During installation, set up a **superuser password** and remember it.

### 3️⃣  Configure PostgreSQL Database
#### **Linux (Ubuntu/Debian-based)**
```sh
sudo -i -u postgres
psql
CREATE DATABASE order_system_db;
CREATE USER dbuser WITH ENCRYPTED PASSWORD 'pass123';
GRANT ALL PRIVILEGES ON DATABASE order_system_db TO dbuser;
\q


### Also, if you have issues with the accessibility of dbuser, grant all privileges
ALTER DATABASE order_system_db OWNER TO dbuser;
GRANT ALL PRIVILEGES ON DATABASE order_system_db TO dbuser;

```

#### **Windows**
1. Open **pgAdmin** or the **psql command-line tool**.
2. Run the following SQL commands:
```sql
CREATE DATABASE order_system_db;
CREATE USER dbuser WITH ENCRYPTED PASSWORD 'pass123';
GRANT ALL PRIVILEGES ON DATABASE order_system_db TO dbuser;
```

### 4️⃣ Install Java & Maven
#### **Linux (Ubuntu/Debian-based)**
```sh
sudo apt install openjdk-17-jdk maven
java -version
mvn -version
```

#### **Windows**
1. Download and install **Java 17** from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
2. Download and install **Maven** from [Apache Maven](https://maven.apache.org/download.cgi) and add it to system environment variables.
3. Verify installation:
```sh
java -version
mvn -version
```

### 5️⃣ Run the Spring Boot Backend
```sh
mvn spring-boot:run
```
📍 Server starts at: **http://localhost:9090**

### 6️⃣ Install and Run the Frontend
#### **Linux & Windows**
```sh
cd order_system_frontend
npm install
npm run serve
```
📍 Frontend starts at: **http://localhost:8080**

---

## Database Schema
📌 *Database schema is automatically created by Hibernate.* However, below are sample SQL insertions for orders and users.

### **Sample User Insertions**
```sql
INSERT INTO users (username, email, password) VALUES ('Admin346', 'admin346@gmail.com', '$2y$10$1GsZ6dL1BIAVzI/kPgK12.Xgyx/yrpE5idzup4Lh0rv29hgmC38Ra');
INSERT INTO users (username, email, password) VALUES ('Client368', 'client368@gmail.com','$2y$10$us.QhWwCAXXAxeUZ3N18wOhIjIsEY5yhwZcD/m6pMYC0OS5WYQ7yq');
```
⚠️ *Ensure passwords are hashed before inserting. You can use* [Bcrypt Online](https://bcrypt.online/) *to hash passwords before executing SQL insertions.*

### **Sample Product Insertions**
```sql
INSERT INTO products (name, cost, quantity) VALUES ('CPUs', 220, 50);
INSERT INTO products (name, cost, quantity) VALUES ('RAMs', 90, 30);
```

### **Sample Order Insertions**
```sql
INSERT INTO orders (user_id, product_id, quantity, total_cost, create_time)
VALUES (4, 2, 2, (SELECT cost FROM products WHERE id = 2) * 2, NOW());

INSERT INTO orders (user_id, product_id, quantity, total_cost, create_time)
VALUES (11, 2, 1, (SELECT cost FROM products WHERE id = 2) * 1, NOW());
```

### **Sample Role Insertions**
📌 *Roles (`ROLE_ADMIN`, `ROLE_USER`) are populated automatically using `@PostConstruct` inside `AuthController`.*

### **Manually Assigning Roles to Users**
📌 *The `user_roles` table is automatically generated via Hibernate due to the `@ManyToMany` relationship between `User` and `Role`.* However, if needed, you can manually assign roles using:
```sql
-- Assign ROLE_ADMIN to a user
INSERT INTO user_roles (user_id, role_id) VALUES (12, (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

-- Assign ROLE_USER to a user
INSERT INTO user_roles (user_id, role_id) VALUES (15, (SELECT id FROM roles WHERE name = 'ROLE_USER'));
```

---

## 📌 Notes
- Hibernate **automatically creates tables** based on JPA entities.
- The `@PostConstruct` annotation **ensures roles are added dynamically** when the application starts.
- If you want to manually insert roles, **use the SQL insertions above.**




## 🔒 Blacklist Tokens Handling

In this system, **JWT tokens** are dynamically created during user authentication and can be invalidated when a user logs out. Instead of manually inserting blacklisted tokens into the database schema, the system **automatically handles token blacklisting** in the following way:

1. **When a user logs out**, their JWT token is added to the `blacklist_tokens` table with an `expires_at` timestamp matching the token’s expiration.
2. **A scheduled task (`@Scheduled`) automatically removes expired tokens** from the blacklist at regular intervals.
3. **Once a token expires (or is removed from the blacklist), it becomes invalid even if it's used in a request**.
4. **There is no need to insert tokens manually** because they are generated dynamically at login.

### **Example Scenario**
:pushpin: Suppose a user logs in and receives the following JWT token:

```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDbGllbnQ2IiwiaWF0IjoxNzM5Mzg3MzE5LCJleHAiOjE3Mzk0NzM3MTl9.HIuwkgwSVkb7xPAZ4-yy_8rNf24OoSwjHCFzIpJRC-ZGtz2WZnou0JSxpCFJRG3uBQU2ZELojI6UuF0mzGYLNA
```

- The token **expires at** `2025-02-12 22:58:00`.
- The user logs out at **22:30**, so we add the token to the `blacklist_tokens` table:

```sql
INSERT INTO blacklist_tokens (token, expires_at)
VALUES ('eyJhbGciOiJIUz...', '2025-02-12 22:58:00');
```

- If the user **tries to reuse this token** after logging out, the system checks the blacklist and rejects the request.
- **At 22:58**, the token expires, and the scheduled task **automatically removes** it from the blacklist.

### **Handling Restarts & Token Cleanup**
If the application is **stopped and restarted**, the blacklist cleanup will still work correctly:
- **Example:** If a token expires at `22:58` but the app is restarted at `23:05` for example, the scheduled cleanup task will **still remove it** when it runs.
- This prevents old tokens from staying in the database **longer than it is necessary**.





