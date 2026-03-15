package org.hamisi.repositories;

import org.hamisi.models.Student;

import java.util.List;

public interface StudentRepository {
    public void addStudent(Student student);
    public List<Student> getAllStudent();
    public void updateStudent(int id);
    public void deleteStudent(int id);
}
