package org.chweya;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.chweya.models.Student;
import org.chweya.models.Course;
import org.chweya.repositories.CourseRepository;

public class ConsoleMenu {

    private Scanner scanner = new Scanner(System.in);

    private List<Student> students = new ArrayList<>();
    private CourseRepository courseRepository = new CourseRepository();

    public void displayMenu() {

        int choice;

        do {
            System.out.println("\n==== Student Management ====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Course");
            System.out.println("4. View Courses");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    addCourse();
                    break;

                case 4:
                    viewCourses();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 0:
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }

    private void addStudent() {
        System.out.print("Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Student name: ");
        String name = scanner.nextLine();

        System.out.print("Student email: ");
        String email = scanner.nextLine();

        Student student = new Student(id, name, email);
        students.add(student);

        System.out.println("Student added successfully.");
    }

    private void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    private void deleteStudent() {

        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();

        students.removeIf(s -> s.getId() == id);

        System.out.println("Student removed if ID existed.");
    }

    private void addCourse() {

        System.out.print("Course ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Course name: ");
        String name = scanner.nextLine();

        Course course = new Course(id, name);

        courseRepository.addCourse(course);

        System.out.println("Course added successfully.");
    }

    private void viewCourses() {

        List<Course> courses = courseRepository.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course c : courses) {
            System.out.println(c);
        }
    }
}
