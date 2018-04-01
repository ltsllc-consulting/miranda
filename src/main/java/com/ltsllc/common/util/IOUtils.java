package com.ltsllc.common.util;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class IOUtils {
    /**
     *  Write an object to a text file as a JSON string.
     *
     * @param filename The file to write.
     * @param o The object to write.
     * @throws IOException This exception is thrown if there is a problem opening, writing to, or closing the file.
     */
    public static void writeAsJason (String filename, Object o) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(json);
        fileWriter.close();
    }
}
