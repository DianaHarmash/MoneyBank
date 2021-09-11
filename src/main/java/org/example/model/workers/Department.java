package org.example.model.workers;

import java.util.ArrayList;

public class Department {
    private final ArrayList<Employee> employees;

    Department() {
        this.employees = new ArrayList<>();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
