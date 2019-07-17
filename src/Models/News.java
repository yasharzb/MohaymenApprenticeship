package Models;

import java.util.Objects;

public class News {
    private String title;
    private int id;
    private String description;
    private String source;
    private int views;

    public News() {

    }

    public News(String source, int id) {
        this.id = id;
        this.source = source;
        title = findTitle(source);
        description = findDescription(source);
    }

    public News(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    private String findTitle(String source) {
        return source.substring(source.indexOf("<title>") + "<title>".length(), source.indexOf("</title>"));
    }

    private String findDescription(String source) {
        return source.substring(source.indexOf("<description>") + "<description><![CDATA[".length()
                , source.indexOf("]]></description>"));
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " \nViews: " + this.views + "\nTitle: " + this.title +
                "\nDescription:" + this.description;
    }

    public void incrementView() {
        this.views++;
    }

    public int getId() {
        return id;
    }

    public int getViews() {
        return views;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(title, news.title) &&
                Objects.equals(description, news.description) &&
                Objects.equals(source, news.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id, description, source, views);
    }
}
