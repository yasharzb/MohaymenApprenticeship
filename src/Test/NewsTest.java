package Test;

import Models.News;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewsTest {
    private News news;

    @BeforeMethod
    public void initiation() {
        news = mock(News.class);
    }

    @Test
    public void incrementView() {
        final int expected = news.getViews() + 1;
        doAnswer(invocationOnMock -> {
            assertEquals(news.getViews(), expected);
            return null;
        }).when(news).incrementView();
    }


}
