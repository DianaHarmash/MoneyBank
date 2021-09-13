package org.example.model.workers;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Manager extends Employee {
    private final ArrayList<Employee> subjects;

    public Manager(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id, BigDecimal salary) {
        super(name, department, position, dateOfBirth, dateOfEmployment, id, salary);
        this.subjects = new ArrayList<>();
    }

    public ArrayList<Employee> getSubjects() {
        return subjects;
    }
}
