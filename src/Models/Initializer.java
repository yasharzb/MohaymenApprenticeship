package Models;

import org.w3c.dom.NodeList;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class Initializer {
    private static final String SOURCE = "https://www.digitaltrends.com/feed/";
    private static final Initializer INITIALIZER = new Initializer();
    private InputStream inputStream = null;
    private RSS rss;


    private Initializer() {

    }

    public static Initializer getInstance() {
        return INITIALIZER;
    }

    public void main(Connection connection) {
        try {
            inputStream = new URL(SOURCE).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rss = new RSS(getURLContent(inputStream));
        try {
            PreparedStatement contentStatement = connection.prepareStatement("insert into rss." +
                    "newsIndex values (?,?,?);");
            PreparedStatement viewStatement = connection.prepareStatement("insert into rss." +
                    "newsView values (?,?);");
            rss.extractNews(contentStatement, viewStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getURLContent(InputStream in) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1000000];
            int len;
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public RSS getRss() {
        return rss;
    }

    public void incrementNewsView(Statement statement, News news) {
        news.incrementView();
        try {
            statement.executeUpdate("update rss.newsView set View = " + news.getViews() +
                    " WHERE Id = " + news.getId() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
