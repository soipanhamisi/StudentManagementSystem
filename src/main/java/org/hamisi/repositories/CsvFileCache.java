package org.hamisi.repositories;

import org.hamisi.models.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileCache implements LocalFileCachingRepository{

    private File cache = new File("studentCache.txt");
    private final String absoluteCachePath;
    private CsvFileCache(){
        try{
            cache.createNewFile();
            this.absoluteCachePath = cache.getAbsolutePath();
        }catch (IOException e){
            throw new IllegalArgumentException("Error getting cache..." +
                    e.getMessage());
        }
    }
    /**
     * @param student
     */
    @Override
    public void putStudent(Student student) {
        String record = String.valueOf(student.getId()) + ", " +
                student.getName() + ", " + student.getCourse() + ", "
                + student.getAge();
        try(FileWriter fileWriter = new FileWriter(this.absoluteCachePath)){
            fileWriter.write(record);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @return
     */
    @Override
    public Student getStudent() {
        return null;
    }

    /**
     *
     */
    @Override
    public void clearStudent() {

    }
}
