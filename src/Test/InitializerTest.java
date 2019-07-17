package Test;

import Models.Initializer;
import Models.News;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitializerTest {
    private News news;
    private Initializer initializer = Initializer.getInstance();
    private Connection connection;
    private Statement statement;


    @BeforeMethod
    public void initiation() {
        news = new News();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rss", "root"
                    , "");
            statement = connection.createStatement();
            initializer.main(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void main() {
        initializer.main(connection);
        Assert.assertFalse(initializer.getInputStream() == null && initializer.getRss() == null);
    }

    @Test
    public void incrementNewsView() {
        final int expected = news.getViews() + 1;
        initializer.incrementNewsView(statement, news);
        final int actual = news.getViews();
        Assert.assertEquals(actual, expected);
    }
}