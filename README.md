# Student Management System

A console-based application for managing student records using Java, JDBC, and MySQL. This application demonstrates CRUD operations with database migrations managed by Flyway.

## Project Structure

```
StudentManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/org/hamisi/
│   │   │   ├── Main.java                    # Application entry point
│   │   │   ├── ConsoleMenu.java             # Console UI and user interactions
│   │   │   ├── DbMigration.java             # Database migration initialization
│   │   │   ├── models/
│   │   │   │   └── Student.java             # Student entity model
│   │   │   └── repositories/
│   │   │       ├── StudentRepository.java   # Repository contract for student CRUD operations
│   │   │       └── SqlJdbcStudentRepository.java # JDBC implementation of the repository
│   │   └── resources/
│   │       └── db/migration/
│   │           ├── V1__init_schema.sql      # Schema creation script
│   │           └── V2__Insert_dummy_data.sql # Sample data insertion
│   └── test/
│       └── java/                            # Unit tests (empty)
├── target/                                  # Compiled classes and build artifacts
├── pom.xml                                  # Maven configuration file
└── README.md                                # This file
```

## Class Descriptions

### Main.java
Entry point of the application. Initializes the console menu and starts the user interface.

### ConsoleMenu.java
Handles all user interactions through a command-line menu. Displays options for adding, viewing, updating, and deleting student records. Accepts comma-separated input from users and delegates operations through the repository abstraction.

### DbMigration.java
Manages database initialization using Flyway. Loads database credentials from environment variables, configures the MySQL connection, and executes pending database migrations automatically when the application starts.

### Student.java
Entity model representing a student record. Contains fields for id (auto-increment), name, course, and age. Provides constructors for creating new students (without id) and retrieving existing students (with id).

### StudentRepository.java
Interface that defines the student data access contract. Declares the core CRUD operations used by the application so the console layer depends on an abstraction rather than a specific database implementation.

### SqlJdbcStudentRepository.java
Concrete JDBC implementation of `StudentRepository`. Connects to MySQL using environment-based configuration, executes SQL statements with `PreparedStatement`, and maps database rows to `Student` objects.

## Database Migrations

### V1__init_schema.sql
Creates the students table with the following schema:
- id: INT, auto-increment primary key
- name: VARCHAR(255)
- course: VARCHAR(255)
- age: INT

### V2__Insert_dummy_data.sql
Populates the students table with 50 sample records for testing purposes.

## Requirements

### System Requirements
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- MySQL Server 8.0 or higher

### Dependencies (managed by Maven)
- MySQL Connector/J 9.6.0
- Flyway Core 12.0.3 (database migration tool)
- dotenv-java 3.0.0 (environment variable loader)

## Setup Instructions

### 1. Prerequisites
Ensure MySQL is installed and running on localhost:3306

### 2. Create Database
```sql
CREATE DATABASE collegeDB;
```

### 3. Environment Configuration
Create a `.env` file in the project root directory with the following content:
```
DB_URL=jdbc:mysql://localhost:3306/collegeDB
DB_USER=your_mysql_username
DB_PASS=your_mysql_password
```

Replace `your_mysql_username` and `your_mysql_password` with your actual MySQL credentials.

### 4. Build the Project
```bash
mvn clean package
```

### 5. Run the Application

**From IDE:**
Run the `Main.java` file directly from your IDE (IntelliJ IDEA, Eclipse, etc.)

**From Command Line:**
```bash
java -jar target/StudentManagementSystem.jar
```

## Application Usage

Once the application starts, you will see a menu with the following options:

1. **Add Student**: Input student name, course, and age (comma-separated format: name,course,age)
2. **View All Students**: Display all students in the database
3. **Update Student**: Modify a student's details by ID (format: id,fieldname,newvalue)
4. **Delete Student**: Remove a student by ID
0. **Exit**: Close the application

## Database Migration

The application automatically runs database migrations on startup:
- V1 creates the initial schema
- V2 inserts 50 sample student records
- Migrations run only once per database

## Notes

- The `.env` file must be present in the working directory when running the application
- The `.env` file should be kept secure and not committed to version control
- Database credentials should be changed from default values in production
- The application uses PreparedStatements to prevent SQL injection attacks

