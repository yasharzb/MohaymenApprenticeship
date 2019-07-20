package Test;

import Models.Constants;
import Models.Initializer;
import Models.News;
import Models.RSS;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.Random;

public class RSSTest {
    private RSS rss;
    private News news;
    private Connection connection;
    private Statement statement;
    private Initializer initializer = Initializer.getInstance();

    @BeforeMethod
    public void initiation() {
        rss = new RSS();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rss", "root"
                    , "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void extractNews() {
        initializer.main(connection);
        rss = initializer.getRss();
        try {
            statement.executeUpdate("drop table newsViewTest;");
            statement.executeUpdate("create table newsViewTest (Id int, View int, primary key (Id));");
            statement.executeUpdate("drop table newsIndexTest;");
            statement.executeUpdate("create table newsIndexTest" +
                    "(Id int, Title TINYTEXT, Description LONGTEXT, primary key (Id));");
            PreparedStatement contentStatement = connection.prepareStatement("insert into rss." +
                    "newsIndexTest values (?,?,?);");
            PreparedStatement viewStatement = connection.prepareStatement("insert into rss." +
                    "newsViewTest values (?,?);");
            rss.extractNews(contentStatement, viewStatement);
            final int expectedIndexSize = getRowCount("newsIndexTest");
            final int expectedViewSize = getRowCount("newsViewTest");
            final int actualIndexSize = getRowCount("newsIndex");
            final int actualViewSize = getRowCount("newsView");
            Assert.assertEquals(actualIndexSize, expectedIndexSize);
            Assert.assertEquals(actualViewSize, expectedViewSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findNewsById() {
        try {
            String tableName = "newsIndexTest";
            news.setId(new Random().nextInt(getRowCount(tableName)) + Constants.INIT_ID);
            ResultSet resultSet = statement.executeQuery("select * from  newsIndexTest where Id="
                    + news.getId() + ";");
            while (resultSet.next()) {
                news = new News(news.getId(), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final News actual = rss.findNewsById(news.getId(), statement);
        Assert.assertEquals(actual, news);
    }

    public int getRowCount(String tableName) throws SQLException {
        Statement statement = this.statement.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

}