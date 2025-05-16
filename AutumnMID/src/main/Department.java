package main;

public enum Department {
    HR("Hr"),
    ENGINEERING("Engineer"),
    MARKETING("Marketing"),
    PLANNING("Planning"),
    ADMIN("Admin"),
    SALES("Sales");

    private final String department;

    Department(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return this.department;
    }
}