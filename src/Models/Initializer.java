package Models;

import org.w3c.dom.NodeList;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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

    public void main() {
        try {
            inputStream = new URL(SOURCE).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rss = new RSS(getURLContent(inputStream));
        rss.extractNews();
        save();
    }

    public void save() {
        try {
            Path jsonFile = Paths.get("./src/Data/rss.json");
            try {
                Files.createFile(jsonFile);
            } catch (Exception ignored) {
            }
            FileWriter jsonWriter = new FileWriter("./src/Data/rss.json");
            jsonWriter.write(rss.toJson());
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void intializeNews() {
        File dataDir = new File("./src/Data");
        try {
            rss = RSS.fromJson(new BufferedReader(new FileReader("./src/Data/rss.json")).readLine());
        } catch (IOException e) {
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
}
