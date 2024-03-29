package Controller;

import Models.Constants;
import Models.Initializer;
import Models.News;

import java.sql.*;
import java.util.Scanner;

public class Controller {
    private static final Controller CONTROLLER = new Controller();
    private Initializer initializer = Initializer.getInstance();
    private Statement statement = null;
    private Connection connection = null;

    private Controller() {

    }

    public static Controller getInstance() {
        return CONTROLLER;
    }

    public void main(String userName, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rss", userName, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Invalid userName or password");
            throw new SQLException("Invalid userName or password");
        }
//        initializer.main(connection);
    }

    public void handleEvents() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (!(command = scanner.nextLine()).equals("exit")) {
            if (command.matches("get\\s[0-9]*"))
                System.out.println(findNews(command));
            else
                System.out.println(Constants.INVALID_COMMAND);
        }
    }

    public String findNews(String input) {
        int id = Integer.parseInt(input.split(" ")[1]);
        try {
            synchronized (initializer.getRss()) {
                News news = initializer.getRss().findNewsById(id, statement);
                initializer.incrementNewsView(statement, news);
                return news.toString();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
