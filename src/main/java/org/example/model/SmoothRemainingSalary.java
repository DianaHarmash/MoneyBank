package org.example.model;

import org.example.model.workers.Department;
import org.example.model.workers.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SmoothRemainingSalary implements SalaryCompute{
    protected int birthBonusValue = 300;
    protected BigDecimal birthBonus(Employee employee){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate birth = LocalDate.parse(employee.getDateOfBirth(), formatter);
        LocalDate date = LocalDate.now();
        return (birth.getMonth().equals(date.getMonth())) ? BigDecimal.valueOf(birthBonusValue) : BigDecimal.ZERO;
    }
    @Override
    public SalaryResult compute(Department department, BigDecimal fullSalary) {
        BigDecimal pureSalary = BigDecimal.ZERO;
        ArrayList<Employee> employees = department.getEmployees();
        for (int x = 0; x<employees.size(); x++ ){
            Employee employee = employees.get(x);
            pureSalary = pureSalary.add(employee.getSalary());
            pureSalary = pureSalary.add(birthBonus(employee));
        }
        BigDecimal remainings = fullSalary.subtract(pureSalary);
        BigDecimal perOne = BigDecimal.valueOf(0.01*(Math.round(100.0*remainings.doubleValue()/employees.size())));
        SalaryResult result = new SalaryResult();
        for (int x = 0; x<employees.size(); x++ ){
            Employee employee = employees.get(x);
            BigDecimal salary = employee.getSalary();
            salary = salary.add(birthBonus(employee));
            salary = salary.add(perOne);
            result.getSalaries().add(new SalaryResult.EmployeeSalary(employee, salary));
        }
        return result;
    }
}
