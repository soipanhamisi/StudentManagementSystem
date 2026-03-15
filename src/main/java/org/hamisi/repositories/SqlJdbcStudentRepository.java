package org.hamisi.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.hamisi.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlJdbcStudentRepository implements StudentRepository{
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private final String USERNAME = dotenv.get("DB_USER");
    private final String PASSWORD = dotenv.get("DB_PASS");
    private final String JDBCURL = dotenv.get("DB_URL");


    /**
     * @param student
     */
    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, course, age) VALUES (?,?,?)";
        try (
                Connection connection = DriverManager.getConnection(JDBCURL, USERNAME,PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getCourse());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Could not get students", e);
        }
    }

    /**
     * @return List<Students> returns a list of all students in the DB
     */
    @Override
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (
                Connection connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                students.add(
                        new Student(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("course"),
                                resultSet.getInt("age")
                        )
                );
            }
            return students;

        }catch (SQLException e){
            throw new RuntimeException("Could not get students", e);
        }
    }

    /**
     * @param columnName
     * @param id
     * @param value
     */
    @Override
    public void updateStudent(String columnName, int id, String value) {
        if(!columnName.matches("^(name|course|age)$")){
            throw new IllegalArgumentException("Invalid column name: " +
                    columnName);
        }
        String sql = "UPDATE students SET " + columnName + " = ? WHERE id = ?";

        try(
                Connection connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            if (columnName == "age"){
                try {
                    preparedStatement.setInt(1, Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid Number entered", e);
                }
                preparedStatement.setInt(2, id);
            } else {
                preparedStatement.setString(1, value);
                preparedStatement.setInt(2, id);
            }
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id
     */
    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, id);
            System.out.println("Rows Affected: " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
