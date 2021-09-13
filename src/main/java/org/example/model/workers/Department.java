package org.example.model.workers;

import java.util.ArrayList;

public class Department {
    private final ArrayList<Employee> employees;
    private final String name;

    public Department(String name) {
        this.employees = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }
}
