package org.hamisi.repositories;

import org.hamisi.models.Student;

import java.util.List;

public interface StudentRepository {
    public void addStudent(Student student);
    public List<Student> getAllStudent();
    public void updateStudent(String columnName, int id, String value);
    public void deleteStudent(int id);
}
