package org.hamisi;

import org.hamisi.models.Student;
import org.hamisi.repositories.SqlJdbcStudentRepository;
import org.hamisi.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * ConsoleMenu class provides a command-line interface for the Student Management System.
 *
 * This class displays an interactive menu that allows users to perform CRUD operations
 * on student records. It handles user input through the console and delegates database
 * operations to the StudentRepository.
 *
 * Supported operations:
 * - Add Student: Input name, course, and age (comma-separated)
 * - View All Students: Display all students in the database
 * - Update Student: Modify a student's details by ID
 * - Delete Student: Remove a student by ID
 * - Exit: Close the application
 */

public class ConsoleMenu {
    private final StudentRepository studentRepository = new SqlJdbcStudentRepository();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        boolean isRunnig = true;
        while(isRunnig){
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    System.out.println("Enter name, course and age(comma-seperated)");
                    String input = scanner.nextLine();
                    String[] parts = input.split(",");

                    Student student = new Student(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()));
                    studentRepository.addStudent(student);
                    System.out.println("Student added successfully...");
                    break;

                case "2":
                    List<Student> allStudents = new ArrayList<>();
                    allStudents = studentRepository.getAllStudent();
                    System.out.println("id, name, course, age");
                    for (Student temp : allStudents){
                        System.out.println(
                                temp.getId() +"," +
                                temp.getName() +"," +
                                temp.getCourse() +"," +
                                temp.getAge()
                                );
                    }
                    break;

                case "3":
                    System.out.println("Enter student id, field name and value(comma-seperated)");
                    String updates = scanner.nextLine();
                    String[] updates2 = updates.split(",");
                    studentRepository.updateStudent(updates2[1].trim(), Integer.parseInt(updates2[0].trim()), updates2[2].trim());
                    break;

                case "4":
                    System.out.println("Enter Student ID");
                    String id = scanner.nextLine();
                    studentRepository.deleteStudent(Integer.parseInt(id));
                    System.out.println("delete sucesful...");
                    break;

                case "0":
                    isRunnig = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("invalid Choice....");
            }
        }

        scanner.close();
    }
}
