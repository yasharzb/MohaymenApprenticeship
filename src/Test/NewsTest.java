package Test;

import Models.News;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {
    private News news;

    @Before
    public void initiation() {
        news = new News();
    }

    @Test
    public void incrementView() {
        final int expected = news.getViews();
        news.incrementView();
        final int actual = news.getViews();
        Assert.assertEquals(actual, expected);
    }
}