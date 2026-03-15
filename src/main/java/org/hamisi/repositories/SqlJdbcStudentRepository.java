package org.hamisi.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import org.hamisi.models.Student;

import java.util.List;

public class SqlJdbcStudentRepository implements StudentRepository{
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private final String USERNAME = dotenv.get("DB_USER");
    private final String PASSWORD = dotenv.get("DB_PASS");
    private final String JDBCURL = dotenv.get("db_url");


    /**
     * @param student
     */
    @Override
    public void addStudent(Student student) {
        String sql = "insert into students (name, course, age) values (?,?,?)";

    }

    /**
     * @return
     */
    @Override
    public List<Student> getAllStudent() {
        return List.of();
    }

    /**
     * @param id
     */
    @Override
    public void updateStudent(int id) {

    }

    /**
     * @param id
     */
    @Override
    public void deleteStudent(int id) {

    }
}
