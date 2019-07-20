package Test;

import Controller.Controller;
import Models.Constants;
import Models.Initializer;
import Models.News;
import Models.RSS;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;

import static org.mockito.Mockito.*;

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
    private News news;
    private RSS rss;

    @BeforeMethod
    public void initiation() {
        rss = mock(RSS.class);
        controller = Controller.getInstance();
        news = new News(6000, "Holy", "kapak");
        news.setViews(0);
        initializer = mock(Initializer.class);
        when(initializer.getRss()).thenReturn(rss);
        when(rss.findNewsById(anyInt(), any(Statement.class))).thenReturn(news);
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

    @Test(expectedExceptions = Exception.class)
    public void findNews() {
        String expected = news.toString();
        String actual = controller.findNews("get 6000");
        Assert.assertEquals(actual, expected);
        controller.findNews("get6000");
        controller.findNews("get news");
    }
}