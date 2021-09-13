package org.example.model;

import org.example.model.workers.Department;
import org.example.model.workers.Employee;
import org.example.model.workers.Manager;
import org.example.model.workers.Other;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class Model {
    private final DataBase dataBase = new DataBase();
    private final ArrayList<Department> departments = new ArrayList<>();

    public Model() {
        try {
            setUpDataBase();
            initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void computeSalary() {
        SalaryBuilder sb = new SalaryBuilder()
                .withDepartment(departments.get(0))
                .withFullSalary(BigDecimal.valueOf(50000))
                .withComputer(new SmoothRemainingSalary());
        SalaryResult sr = sb.build();
        System.out.println(sr);

    }

    private void setUpDataBase() throws SQLException {
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_DEPARTMENT);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_EMPLOYEE);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_SUBORDINATION);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_OTHER);
    }

    private void initializeData() throws SQLException {
        if (dataBase.executeScalar(SQLCommands.SQL_SELECT_AMOUNT_OF_EMPLOYEES) > 0) return;
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_DEPARTMENT);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_EMPLOYEE);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_SUBORDINATION);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_OTHER);
    }

    public ResultSet getFromDB(String message) {
        return dataBase.executeQuery(message);
    }

    public void updateRequest(String message) {
        dataBase.executeUpdate(message);
    }

    private Department getDepartment(ResultSet result) {
        String department = null;
        try {
            department = result.getString(SQLColumns.NAME_OF_DEPARTMENT);
            String finalString = Objects.requireNonNull(department);
            Optional<Department> searchResult = departments.stream().filter(e -> e.getName().equals(finalString)).findFirst();
            if (searchResult.isPresent()) {
                return searchResult.get();
            }
        } catch (SQLException e){ e.printStackTrace(); }
        if (department != null) {
            Department d = new Department(department);
            departments.add(d);
            return d;
        }
        return null;

    }

    private Manager getManager(ResultSet result) {
        try {
            return new Manager(result.getString(SQLColumns.NAME_OF_EMPLOYEE),
                    result.getString(SQLColumns.NAME_OF_DEPARTMENT),
                    result.getString(SQLColumns.POSITION_OF_EMPLOYEE),
                    result.getString(SQLColumns.DATE_OF_BIRTH),
                    result.getString(SQLColumns.DATE_OF_EMPLOYMENT),
                    result.getInt(SQLColumns.ID_OF_EMPLOYEE),
                    BigDecimal.valueOf(result.getInt("salary")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Other getOther(ResultSet other) {
        try {
            return new Other(other.getString(SQLColumns.NAME_OF_EMPLOYEE),
                    other.getString(SQLColumns.NAME_OF_DEPARTMENT),
                    other.getString(SQLColumns.POSITION_OF_EMPLOYEE),
                    other.getString(SQLColumns.DATE_OF_BIRTH),
                    other.getString(SQLColumns.DATE_OF_EMPLOYMENT),
                    other.getInt(SQLColumns.ID_OF_EMPLOYEE),
                    other.getString(SQLColumns.ADDITIONAL_INFO),
                    BigDecimal.valueOf(other.getInt("salary")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Employee getEmployee(ResultSet result) {
        try {
            return new Employee(result.getString(SQLColumns.NAME_OF_EMPLOYEE),
                    result.getString(SQLColumns.NAME_OF_DEPARTMENT),
                    result.getString(SQLColumns.POSITION_OF_EMPLOYEE),
                    result.getString(SQLColumns.DATE_OF_BIRTH),
                    result.getString(SQLColumns.DATE_OF_EMPLOYMENT),
                    result.getInt(SQLColumns.ID_OF_EMPLOYEE),
                    BigDecimal.valueOf(result.getInt("salary")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void subordination (ResultSet resultSet, HashMap<Integer, ArrayList<Employee>> subjects, Employee employee) {
        try {
            int idOfManager = resultSet.getInt(SQLColumns.ID_OF_MANAGER);
            ArrayList<Employee> employeesOfTheManager = subjects.get(idOfManager);
            if (employeesOfTheManager == null) {
                employeesOfTheManager = new ArrayList<>();
                subjects.put(idOfManager, employeesOfTheManager);
            }
            employeesOfTheManager.add(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromTables() throws SQLException{
        Department mainDepartment = new Department("Whole office");
        departments.add(mainDepartment);

        ResultSet resultSet = dataBase.executeQuery(SQLCommands.SQL_SELECT_JOIN_FULL_TABLE);
        HashMap<Integer, ArrayList<Employee>> subjects = new HashMap<>();
        while (resultSet.next()) {
            Department department = getDepartment(resultSet);
            if (department != null) {
                ArrayList<Employee> linkToDepartment = department.getEmployees();
                if (resultSet.getObject(SQLColumns.ID_OF_MANAGER) == null) {
                    Manager manager = getManager(resultSet);
                    linkToDepartment.add(manager);
                    mainDepartment.getEmployees().add(manager);
                } else if (resultSet.getObject(SQLColumns.ID_OF_THE_OTHER_EMPLOYEE) != null) {
                    Other other = getOther(resultSet);
                    linkToDepartment.add(other);
                    mainDepartment.getEmployees().add(other);
                    subordination(resultSet, subjects, other);
                } else {
                    Employee employee = getEmployee(resultSet);
                    linkToDepartment.add(employee);
                    mainDepartment.getEmployees().add(employee);
                    subordination(resultSet, subjects, employee);
                }
            }
        }
        getArrayListToManagers(subjects);
    }

    private void getArrayListToManagers(HashMap<Integer, ArrayList<Employee>> subjects) {
        for (int x = 0; x < departments.size(); x++) {
            ArrayList<Employee> employees = departments.get(x).getEmployees();
            for (int y = 0; y < employees.size(); y++) {
                if (employees.get(y) instanceof Manager) {
                    ArrayList<Employee> subjectsOfManager = ((Manager) employees.get(y)).getSubjects();
                    subjectsOfManager = subjects.get(employees.get(y).getId());
                }
            }
        }
    }
}
