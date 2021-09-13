package org.example.controller;

import org.example.model.Model;
import org.example.model.SQLColumns;
import org.example.model.SQLCommands;
import org.example.view.ConstString;
import org.example.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(model);
        this.model = model;
        this.view = view;
    }

    private int getInt() {
        while(true) {
            try {
               return new Scanner(System.in).nextInt();
            } catch (IllegalArgumentException e) {
                view.printStringLN(ConstString.ILLEGAL_FORMAT);
            }
        }
    }

    private void printMenu() {
        view.printStringLN(ConstString.MENU);
        view.printStringLN(ConstString.COMPUTE_SALARY);
        view.printStringLN(ConstString.SELECT_ALL_EMPLOYEE);
        view.printStringLN(ConstString.SORT_ALL_EMPLOYEE_BY_NAME);
        view.printStringLN(ConstString.SORT_ALL_EMPLOYEE_BY_DATE_OF_BIRTH);
        view.printStringLN(ConstString.EXIT);
        view.printString(ConstString.WRITE_DOWN_A_NUMBER_OF_WHAT_YOU_WANT);
    }

    private void getFromDB(String message) throws SQLException {
        ResultSet resultSet = model.getFromDB(message);
        while(resultSet.next()) {
            view.printStringLN(resultSet.getString(SQLColumns.ID_OF_EMPLOYEE) + "  " + resultSet.getString(SQLColumns.NAME_OF_EMPLOYEE) + " (" + resultSet.getString(SQLColumns.DATE_OF_BIRTH) + ") " + resultSet.getString(SQLColumns.NAME_OF_DEPARTMENT) + "  [" + resultSet.getString(SQLColumns.DATE_OF_EMPLOYMENT) + "]  " + resultSet.getString(SQLColumns.POSITION_OF_EMPLOYEE));
        }
    }


    private void menu() throws SQLException {
        int x = -1;
        while (x != 5) {
            printMenu();
            x = getInt();
            switch (x) {
                case 1 -> model.computeSalary();
                case 2 -> getFromDB(SQLCommands.SQL_SELECT_JOIN_FULL_TABLE);
                case 3 -> getFromDB(SQLCommands.SQL_SELECT_ALL_EMPLOYEES_SURNAME_ASC);
                case 4 -> getFromDB(SQLCommands.SQL_SELECT_ALL_EMPLOYEES_DATE_OF_EMPLOYMENT_ASC);
                case 5 -> {
                    return;
                }
            }
        }
    }

    public void processMoneyBank() {
        try {
            model.getDataFromTables();
            menu();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
