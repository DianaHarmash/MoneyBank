package org.example.model.workers;

public class Other extends Employee {
    private final String description;

    Other(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id, String description) {
        super(name, department, position, dateOfBirth, dateOfEmployment, id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
