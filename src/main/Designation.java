package main;

public enum Designation {
    CEO("Chief Executive Officer"),
    GENERAL_MANAGER("General Manager"),
    DIRECTOR("Director"),
    CHIEF_OPERATING_OFFICER("Chief Operating Officer");

    private final String designation;

    Designation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return this.designation;
    }
}