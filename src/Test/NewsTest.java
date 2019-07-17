package Test;

import Models.News;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewsTest {
    private News news;

    @BeforeMethod
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
