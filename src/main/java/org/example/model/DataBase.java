package org.example.model;

import org.postgresql.Driver;

import java.sql.*;
import java.util.Objects;

public class DataBase {
    private final String url = "jdbc:postgresql://localhost:5432/myDataBase";
    private final String user = "postgres";
    private final String password = "qwerty1234";

    private Connection connect() {
        Class<Driver> SQLClass = org.postgresql.Driver.class;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection is successful");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void executeUpdate(String SQLCommand) {
        try (Connection connection = connect();
             Statement statement = Objects.requireNonNull(connection).createStatement()) {
            statement.executeUpdate(SQLCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeScalar(String SQLCommand) {
        ResultSet result = this.executeQuery(SQLCommand);
        try{
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                result.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public ResultSet executeQuery(String SQLCommand) {
        ResultSet set;
        try (Connection connection = connect()) {
            Statement statement = Objects.requireNonNull(connection).createStatement();
            set = statement.executeQuery(SQLCommand);
            return set;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void displaySQL(ResultSet resultSet) {
        System.out.println(resultSet.toString());
    }

}
