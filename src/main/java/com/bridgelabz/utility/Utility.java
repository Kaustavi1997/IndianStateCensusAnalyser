package com.bridgelabz.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Utility {
    public static<E> void jsonFileWriter(String filePath, String data) throws IOException {
        Writer writer = new FileWriter(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(data);
        gson.toJson(jsonElement, writer);
        writer.flush();
        writer.close();
    }
}
