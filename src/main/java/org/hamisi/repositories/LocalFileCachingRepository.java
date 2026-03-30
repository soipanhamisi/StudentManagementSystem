package org.hamisi.repositories;

import org.hamisi.models.Student;

public interface LocalFileCachingRepository {
    public void initFile();
    public void loadCache();
    public void putStudent(Student student);
    public Student getStudent(int id);
    public void clearStudent();
    public void commitToCache();
}
