package org.hamisi.repositories;

import org.hamisi.models.Student;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvFileCache implements LocalFileCachingRepository {
    private final File cacheFile = new File("localCache.txt");
    private final Map<Integer, Student> studentCache = new HashMap<>();
    private boolean isLoaded = false;

    /**
     * the function will create a file in the root directory of the project
     */
    @Override
    public void initFile() {
        try {
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize cache file", e);
        }
    }

    /**
     * Copies the contents of the file onto a hashmap
     */
    @Override
    public void loadCache() {
        initFile();
        studentCache.clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cacheFile))) {
            String record;
            while ((record = bufferedReader.readLine()) != null) {
                if (record.trim().isEmpty()) {
                    continue;
                }

                String[] parts = record.split(",");
                if (parts.length < 4) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String course = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    studentCache.put(id, new Student(id, name, course, age));
                } catch (NumberFormatException ignored) {
                    // Skip malformed rows and continue loading valid records.
                }
            }

            this.isLoaded = true;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load cache", e);
        }
    }

    /**
     * adds student to the hashmap
     * @param student
     */
    @Override
    public void putStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("student cannot be null");
        }
        if (!this.isLoaded) {
            loadCache();
        }

        this.studentCache.put(student.getId(), student);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Student getStudent(int id) {
        if (!this.isLoaded) {
            loadCache();
        }
        return this.studentCache.get(id);
    }

    /**
     *
     */
    @Override
    public void clearStudent(int id) {
        if (!this.isLoaded) {
            loadCache();
        }
        this.studentCache.remove(id);
    }

    /**
     *
     */
    @Override
    public void commitToCache() {
        initFile();
        if (!this.isLoaded) {
            loadCache();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile, false))) {
            for (Student student : studentCache.values()) {
                String id = String.valueOf(student.getId());
                String name = student.getName();
                String course = student.getCourse();
                String age = String.valueOf(student.getAge());
                String record = id + "," + name +
                        "," + course + "," + age;

                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to commit cache", e);
        }
    }
}
