package org.example.model.workers;

import java.math.BigDecimal;

public class Employee {
    private final String name;
    private final String department;
    private final String position;
    private final String dateOfBirth;
    private final String dateOfEmployment;
    private final BigDecimal salary;
    private final int id;

    public BigDecimal getSalary() {
        return salary;
    }

    public Employee(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id, BigDecimal salary) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;
        this.id = id;
        this.salary = salary;
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
