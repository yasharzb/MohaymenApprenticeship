package Main;

import Controller.Controller;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        try {
            controller.main("root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        controller.handleEvents();
    }
}
