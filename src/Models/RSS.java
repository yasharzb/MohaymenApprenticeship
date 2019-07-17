package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RSS {
    private String url;
    private String content;

    public RSS() {

    }

    public RSS(String content) {
        this.content = content;
    }

    public News findNewsById(int id, Statement statement) {
        ResultSet rssResult;
        News news = null;
        try {
            rssResult = statement.executeQuery("select * from rss.newsIndex where Id = " + id + ";");
            while (rssResult.next()) {
                news = new News(rssResult.getInt(1), rssResult.getString(2),
                        rssResult.getString(3));
                Statement viewStatement = statement.getConnection().createStatement();
                ResultSet viewResult = viewStatement.executeQuery("select * from rss.newsView where Id =" +
                        news.getId() + ";");
                while (viewResult.next())
                    news.setViews(viewResult.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void extractNews(PreparedStatement content, PreparedStatement view) {
        int id = 1, i = 0;
        for (i = this.content.indexOf("<item>", i); i < this.content.lastIndexOf("<item>"); ) {
            String item = this.content.substring(this.content.indexOf("<item>", i) + "<item>".length()
                    , this.content.indexOf("</item>", this.content.indexOf("<item>", i)));
            News news = new News(item, Constants.INIT_ID + id);
            try {
                content.setInt(1, news.getId());
                content.setString(2, news.getTitle());
                content.setString(3, news.getDescription());
                view.setInt(1, news.getId());
                view.setInt(2, 0);
                content.execute();
                view.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            id++;
            i = this.content.indexOf("<item>", i + 1);
        }
    }
}
