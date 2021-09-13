package org.example.model;

import org.example.model.workers.Employee;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SalaryResult {
    public static class EmployeeSalary{
        private Employee employee;
        private BigDecimal salary;

        public EmployeeSalary(Employee employee, BigDecimal salary) {
            this.employee = employee;
            this.salary = salary;
        }

        public Employee getEmployee() {
            return employee;
        }

        public BigDecimal getSalary() {
            return salary;
        }
    }

    private final ArrayList<EmployeeSalary> salaries = new ArrayList<>();

    public ArrayList<EmployeeSalary> getSalaries() {
        return salaries;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (EmployeeSalary es: salaries) {
            builder.append(es.getEmployee().getId()).append("  ")
                    .append(es.getEmployee().getName()).append("  ")
                    .append(es.getSalary()).append("\n");
        }
        return builder.toString();
    }
}
