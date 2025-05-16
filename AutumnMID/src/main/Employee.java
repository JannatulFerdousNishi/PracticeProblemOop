package main;

import java.time.LocalDate;

public class Employee implements Comparable<Employee> {
    private final int id;
    private final String name;
    private final int salary;
    private final String gender;
    private final Designation designation;
    private final Department department;
    private final LocalDate dateOfBirth;
    private final LocalDate joiningDate;

    public Employee(int id, String name, int salary, String gender, Designation designation, Department department, LocalDate dateOfBirth, LocalDate joiningDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.designation = designation;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
        this.joiningDate = joiningDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Department getDepartment() {
        return department;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    @Override
    public String toString() {
        return "Employee(" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                ", designation=" + designation +
                ", department=" + department +
                ", dateOfBirth=" + dateOfBirth +
                ", joiningDate=" + joiningDate +
                ')';
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }
}