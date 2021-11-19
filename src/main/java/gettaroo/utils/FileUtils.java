package gettaroo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gettaroo.Gettaroo;

import java.io.*;
import java.util.List;

public class FileUtils {

    public static void loadToJsonObject(File path, Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (object != null) {

            try {
                if (!path.exists()) {
                    path.createNewFile();
                }

                FileWriter writer = new FileWriter(path);
                gson.toJson(object, writer);
                writer.flush();
            } catch (IOException e) {
            }
        }
    }
}
