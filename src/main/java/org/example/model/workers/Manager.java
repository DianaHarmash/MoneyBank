package org.example.model.workers;

import java.util.ArrayList;

public class Manager extends Employee {
    private final ArrayList<Employee> subjects;

    Manager(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id) {
        super(name, department, position, dateOfBirth, dateOfEmployment, id);
        this.subjects = new ArrayList<>();
    }

    public ArrayList<Employee> getSubjects() {
        return subjects;
    }
}
