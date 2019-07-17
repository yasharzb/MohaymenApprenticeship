package Models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeTest;

public class NewsTest {
    private News news;

    @BeforeTest
    public void initiation() {
        news = new News();
    }

    @Test
    public void incrementView() {
        final int expected = news.getViews() + 1;
        System.out.println(expected);
        news.incrementView();
        final int actual = news.getViews();
        Assert.assertEquals(actual, expected);
    }
}