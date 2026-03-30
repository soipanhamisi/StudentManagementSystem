package org.hamisi.repositories;

import org.hamisi.models.Student;

public interface LocalFileCachingRepository {

    public void initFile();
    /**
     * Function responsible for creating a new file if none exists.
     * The file is in the form of a CSV .txt file.
     * */
    public void loadCache();
    /**
     * copies contents of the file onto a hashmap */
    public void putStudent(Student student);
    public Student getStudent(int id);
    public void clearStudent(int id);
    /**
     * overwrites the local file with the contents of the hashmap
     * */
    public void commitToCache();

}
