package Main;

import Controller.Controller;


public class Main {

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.main("root", "");
        controller.handleEvents();
    }
}
