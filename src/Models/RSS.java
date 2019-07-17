package Models;

import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RSS {
    private String url;
    private String name;
    private String content;
    private HashMap<Integer, News> items = new HashMap<>();

    public RSS() {

    }

    public RSS(String content) {
        this.content = content;
    }

    public static News findNewsById(int id, RSS rss) {
        if (rss.items.containsKey(id))
            return rss.items.get(id);
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public HashMap<Integer, News> getItems() {
        return items;
    }

    protected void extractNews(PreparedStatement content, PreparedStatement view) {
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
            items.put(Constants.INIT_ID + id, news);
            id++;
            i = this.content.indexOf("<item>", i + 1);
        }
    }

    public static RSS fromJson(String json) {
        return new Gson().fromJson(json, RSS.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
