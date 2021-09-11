package org.example;

import org.example.model.DataBase;
import org.example.model.SQLCommands;
import org.example.view.View;
import org.example.model.Model;
import org.example.controller.Controller;

public class Main {
    public static void main( String[] args ) {
        View view = new View();
        Model model= new Model();

        Controller controller = new Controller(model, view);
        controller.processMoneyBank();
    }
}
