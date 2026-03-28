package org.hamisi.repositories;

import org.hamisi.models.Student;

public interface LocalFileCachingRepository {
    public void putStudent(Student student);
    public Student getStudent();
    public void clearStudent();
}
