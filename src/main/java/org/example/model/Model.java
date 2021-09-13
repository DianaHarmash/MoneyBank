package org.example.model;

import org.example.model.workers.Department;
import org.example.model.workers.Employee;
import org.example.model.workers.Manager;
import org.example.model.workers.Other;

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

    /**
     * The method which calculates a salary depending on the method of calculation,
     * the method of calculation is set by either of classes SmoothRemainingsSalary or ProportionalSalary
     */

    public void computeSalary() {
        SalaryBuilder sb = new SalaryBuilder()
                .withDepartment(departments.get(0))
                .withFullSalary(BigDecimal.valueOf(50000))
              //  .withComputer(new SmoothRemainingSalary());
                .withComputer(new ProportionalSalary());
        SalaryResult sr = sb.build();
        System.out.println(sr);

    }

    /**
     * The method creates tables in data base if they does not exist
     * @throws SQLException
     */

    private void setUpDataBase() throws SQLException {
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_DEPARTMENT);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_EMPLOYEE);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_SUBORDINATION);
        dataBase.executeUpdate(SQLCommands.SQL_CREATE_TABLE_OTHER);
    }

    /**
     * The method initialize data base if it wasn't initialized before
     * @throws SQLException
     */

    private void initializeData() throws SQLException {
        if (dataBase.executeScalar(SQLCommands.SQL_SELECT_AMOUNT_OF_EMPLOYEES) > 0) return;
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_DEPARTMENT);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_EMPLOYEE);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_SUBORDINATION);
        dataBase.executeUpdate(SQLCommands.SQL_INSERT_INTO_OTHER);
    }

    /**
     * The method to get data from data base
     * @param message is an SQL command
     * @return ResultSet with data acquired from data base
     */

    public ResultSet getFromDB(String message) {
        return dataBase.executeQuery(message);
    }

    /**
     * The method which makes data base updated
     * @param message is an SQL command which tell what to update in data base
     */

    public void updateRequest(String message) {
        dataBase.executeUpdate(message);
    }

    /**
     * The method which creates a new object Department (if such one is absent in ArrayList departments) or returns
     * the needed object from departments
     * @param result is data from data base
     * @return Department
     */

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

    /**
     * The method which creates a new Manager
     * @param result is a data from data base
     * @return new Manager
     */

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

    /**
     * The method which creates a new Other
     * @param other is a data from data base
     * @return new Other
     */

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

    /**
     * The method which creates new Employee
     * @param result is a data from data base
     * @return new Employee
     */

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

    /**
     * The method to set employees which obey to a manager
     * @param resultSet is a data from data base
     * @param subjects is an HasMap of existing employees obeying to a manager, in which key is a manager's id and a value is ArrayList of employees
     * @param employee is an employee to which should be insert into the ArrayList of employees
     */

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

    /**
     * The method which creates a full set of all needed for working of program classes.
     * It get a data from data base and check what it must create either a manager or an other or an employee
     * @throws SQLException
     */

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

    /**
     * The method which gets all employees from ArrayList employees (it is a value in the HashMap subjects) and copy it into the object of class manager
     * @param subjects is a HashMap containing a list of subjects
     */

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
