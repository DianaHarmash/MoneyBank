package org.example.controller;

import org.example.model.Model;
import org.example.view.View;

import java.sql.SQLException;
import java.util.Objects;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(model);
        this.model = model;
        this.view = view;
    }

    public void processMoneyBank() {
        try {
            model.getDataFromTables();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
