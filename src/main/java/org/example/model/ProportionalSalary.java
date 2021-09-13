package org.example.model;

import org.example.model.workers.Department;
import org.example.model.workers.Employee;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ProportionalSalary  extends SmoothRemainingSalary{
    @Override
    public SalaryResult compute(Department department, BigDecimal fullSalary) {
        BigDecimal pureSalary = BigDecimal.ZERO;
        BigDecimal withBirthBonusSalary = BigDecimal.ZERO;
        ArrayList<Employee> employees = department.getEmployees();
        for (int x = 0; x<employees.size(); x++ ){
            Employee employee = employees.get(x);
            pureSalary = pureSalary.add(employee.getSalary());
            withBirthBonusSalary = withBirthBonusSalary.add(employee.getSalary());
            withBirthBonusSalary = withBirthBonusSalary.add(birthBonus(employee));
        }
        BigDecimal remainings = fullSalary.subtract(withBirthBonusSalary);
        BigDecimal perOneScale = BigDecimal.valueOf(remainings.doubleValue() / pureSalary.doubleValue());
        SalaryResult result = new SalaryResult();
        for (int x = 0; x<employees.size(); x++ ){
            Employee employee = employees.get(x);
            BigDecimal salary = employee.getSalary();
            BigDecimal bonus = salary.multiply(perOneScale);
            bonus = BigDecimal.valueOf(0.01*Math.round(bonus.doubleValue()*100));
            salary = salary.add(bonus);
            salary = salary.add(birthBonus(employee));
            result.getSalaries().add(new SalaryResult.EmployeeSalary(employee, salary));
        }
        return result;
    }
}
