package autumnmid;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {
    private final int id;
    private final String name;
    private final float salary;
    private final String gender;
    private final String desig;
    private final String dept;
    public final LocalDate dob;
    public final LocalDate doj;

    public Employee(int id, String name, float salary, String gender, String desig, String dept, LocalDate dob, LocalDate doj) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.desig = desig;
        this.dept = dept;
        this.dob = dob;
        this.doj = doj;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public String getDesig() {
        return desig;
    }

    public String getDept() {
        return dept;
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDate getDoj() {
        return doj;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                ", Name='" + name + '\'' +
                ", Salary=" + salary +
                ", Gen='" + gender + '\'' +
                ", Desig='" + desig + '\'' +
                ", Dept='" + dept + '\'' +
                ", Dob=" + dob +
                ", Doj=" + doj;
    }
}