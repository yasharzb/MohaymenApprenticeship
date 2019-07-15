package Models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class RSS {
    private String url;
    private String name;
    private String content;
    private HashMap<Integer, News> items = new HashMap<>();

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

    protected void extractNews() {
        int id = 1, i = 0;
        for (i = content.indexOf("<item>", i); i < content.lastIndexOf("<item>"); ) {
            String item = content.substring(content.indexOf("<item>", i) + "<item>".length()
                    , content.indexOf("</item>", content.indexOf("<item>", i)));
            items.put(Constants.INIT_ID + id, new News(item, Constants.INIT_ID + id));
            id++;
            i = content.indexOf("<item>", i + 1);
        }
    }

    public static RSS fromJson(String json) {
        return new Gson().fromJson(json, RSS.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
