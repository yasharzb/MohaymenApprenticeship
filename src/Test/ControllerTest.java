package Test;

import Controller.Controller;
import Models.Constants;
import Models.Initializer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ControllerTest {
    private Controller controller;
    private Initializer initializer;
    private Connection connection;
    private Statement statement;

    @BeforeMethod
    public void initiation() {
        controller = Controller.getInstance();
        initializer = Initializer.getInstance();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rss", "root"
                    , "");
            statement = connection.createStatement();
            controller.setConnection(connection);
            controller.setStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializer.main(connection);
    }


    @Test
    public void validMain() throws SQLException {
        controller.main("root", "");
    }

    @Test(expectedExceptions = SQLException.class)
    public void inValidMain() throws SQLException {
        controller.main("invalidUser", "invalidPassword");
    }

    @Test
    public void handleEvents() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/Input"));
            String command, check;
            for (int i = 0; i < reader.lines().count(); i++) {
                command = reader.readLine();
                if (command.matches("get\\s[0-9]*"))
                    check = controller.findNews(command);
                else
                    check = Constants.INVALID_COMMAND;
                switch (i) {
                    case 0:
                    case 1:
                    case 3:
                    case 4:
                        Assert.assertEquals(check, Constants.INVALID_COMMAND);
                        break;
                    case 2:
                        Assert.assertNotEquals(check, Constants.INVALID_COMMAND);
                        Assert.assertNotEquals(check, Constants.NOT_FOUND);
                        break;
                    case 5:
                        Assert.assertEquals(check, Constants.NOT_FOUND);
                        break;
                    case 6:
                        break;
                }
            }
        } catch (Exception e) {

        }
    }

    @Test
    public void findNews() {
        int randomId = 1 + Constants.INIT_ID;
        final String actualTrue = controller.findNews("get " + (randomId));
        System.out.println(actualTrue);
        Assert.assertNotEquals(Constants.NOT_FOUND, actualTrue);
        randomId = 2 * Constants.INIT_ID;
        final String actualFalse = controller.findNews("get " + (randomId));
        Assert.assertEquals(Constants.NOT_FOUND, actualFalse);
    }
}