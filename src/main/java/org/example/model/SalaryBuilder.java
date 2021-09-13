package org.example.model;

import org.example.model.workers.Department;

import java.math.BigDecimal;

public class SalaryBuilder {
    private Department department;
    private SalaryCompute computer;
    private BigDecimal fullSalary;
    SalaryBuilder withDepartment(Department department){
        this.department = department;
        return this;
    }
    SalaryBuilder withComputer(SalaryCompute  computer){
        this.computer = computer;
        return this;
    }
    SalaryBuilder withFullSalary(BigDecimal fullSalary){
        this.fullSalary = fullSalary;
        return this;
    }
    SalaryResult build(){
        if (this.computer == null) throw new IllegalStateException("no computer");
        if (this.department == null) throw new IllegalStateException("no department");
        return this.computer.compute(department, fullSalary);
    }
}
