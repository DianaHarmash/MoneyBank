package org.example.model;

import org.example.model.workers.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void getDataFromTables() throws SQLException{
        ResultSet resultSet = dataBase.executeQuery(SQLCommands.SQL_SELECT_JOIN_FULL_TABLE);
        while (resultSet.next()){
            if (resultSet.getObject("id_of_manager")==null) {
                // TODO: create manager
            } else if (resultSet.getObject("id_of_other_employee")!=null) {
                // TODO: create other
            } else {
                // TODO: create regular
            }
        }

    }

}
