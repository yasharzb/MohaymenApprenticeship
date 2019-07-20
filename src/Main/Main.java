package Main;

import Controller.Controller;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        try {
            controller.main("root", "123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        controller.handleEvents();
    }
}
