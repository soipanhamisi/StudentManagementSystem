package org.hamisi.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.hamisi.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentRepository class handles all database operations (CRUD) for the Student entity.
 * This class is responsible for connecting to a MySQL database and performing
 * Create, Read, Update, and Delete operations using JDBC PreparedStatements.
 * Database credentials are loaded from environment variables via dotenv.
 */
public class StudentRepository {
    private final String PASSWORD;
    private final String USERNAME;
    private final String URL = "jdbc:mysql://localhost:3306/collegeDB";

    public StudentRepository() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.USERNAME = dotenv.get("DB_USER");
        this.PASSWORD = dotenv.get("DB_PASS");
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, course, age) VALUES(?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setString(2, student.getCourse());
            statement.setInt(3, student.getAge());

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Failed to Insert Student", e);
        }
    }

    public int[] addStudentBatch(List<Student> students) {
        String sql = "INSERT INTO students (name, course, age) VALUES(?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Student student : students) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getCourse());
                statement.setInt(3, student.getAge());
                statement.addBatch();
            }

            return statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM STUDENTS";
        List<Student> students = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql)
                ){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("course"),
                        resultSet.getInt("age")
                );
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudentId(int id) {
        String sql = "SELECT * FROM STUDENTS WHERE id = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement((sql));
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("course"),
                    resultSet.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int id){
        String sql = "DELETE FROM students WHERE id = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(int id, String columnName, String value){
        if (!columnName.matches("^(name|course|age)$")) {
            throw new IllegalArgumentException("Invalid column name: " + columnName);
        }

        String sql = "UPDATE students SET " + columnName + " = ? WHERE id = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}