package Models;

import Models.News;
import Models.RSS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class RSSTest {
    private RSS rss;
    private News news;
    private Connection connection;
    private Statement statement;

    @Before
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
        news = new News(50003, "Walmart post-Prime Day sale: 4K TV, Apple, and Nintendo Switch deals",
                "Prime Day 2019 has come to an end for Amazon, but that doesn't mean the deals are over." +
                        " Walmart's Prime Day sale. Walmart's Prime Day sale lasts all day today, extending a whole " +
                        "extra day beyond Amazon's shopping extravaganza.");

    }


    @Test
    public void findNewsById() {
        final News actual = rss.findNewsById(news.getId(), statement);
        Assert.assertEquals(actual, news);
    }

    @Test
    public void extractNews() {
    }
}