package org.example.model.workers;

public class Employee {
    private final String name;
    private final String department;
    private final String position;
    private final String dateOfBirth;
    private final String dateOfEmployment;
    private final int id;

    Employee(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    public int getId() {
        return id;
    }
}
