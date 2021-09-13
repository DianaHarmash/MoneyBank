package org.example.model.workers;

import java.math.BigDecimal;

public class Other extends Employee {
    private final String description;

    public Other(String name, String department, String position, String dateOfBirth, String dateOfEmployment, int id, String description, BigDecimal salary) {
        super(name, department, position, dateOfBirth, dateOfEmployment, id, salary);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
