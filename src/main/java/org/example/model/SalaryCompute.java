package org.example.model;

import org.example.model.workers.Department;

import java.math.BigDecimal;

public interface SalaryCompute {
    SalaryResult compute(Department department, BigDecimal fullSalary);
}
