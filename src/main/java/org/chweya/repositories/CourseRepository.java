package org.chweya.repositories;

import java.util.ArrayList;
import java.util.List;
import org.chweya.models.Course;

public class CourseRepository {

    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course findCourseById(int id) {
        for (Course c : courses) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean removeCourse(int id) {
        Course c = findCourseById(id);
        if (c != null) {
            courses.remove(c);
            return true;
        }
        return false;
    }
}
