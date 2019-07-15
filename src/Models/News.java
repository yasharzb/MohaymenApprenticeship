package Models;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class News {
    private String title;
    private int id;
    private String description;
    private String content;
    private String detailedURL;
    private String source;
    private int views;

    public News(String source, int id) {
        this.id = id;
        this.source = source;
        title = findTitle(source);
        description = findDescription(source);
        try {
            Path jsonFile = Paths.get("./src/Data/" + id + ".json");
            Path xmlFile = Paths.get("./src/Data/" + id + ".xml");
            try {
                Files.createFile(jsonFile);
                Files.createFile(xmlFile);
            } catch (Exception ignored) {
            }
            FileWriter jsonWriter = new FileWriter("./src/Data/" + id + ".json");
            jsonWriter.write(this.toJson());
            jsonWriter.close();
            FileWriter xmlWriter = new FileWriter("./src/Data/" + id + ".xml");
            xmlWriter.write(this.toXML());
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String findTitle(String source) {
        return source.substring(source.indexOf("<title>") + "<title>".length(), source.indexOf("</title>"));
    }

    private String findDescription(String source) {
        return source.substring(source.indexOf("<description>") + "<description><![CDATA[".length()
                , source.indexOf("]]></description>"));
    }

    private String toJson() {
        return new Gson().toJson(this);
    }

    public static News fromJson(String json) {
        return new Gson().fromJson(json, News.class);
    }

    private String toXML() {
        return "<item>\n" + source + "\n</item>";
    }

    private String findContent(String source) {
        return null;
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
}
