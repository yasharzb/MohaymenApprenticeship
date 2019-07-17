package Test;

import Controller.Controller;
import Models.Constants;
import Models.Initializer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    public void main() {

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