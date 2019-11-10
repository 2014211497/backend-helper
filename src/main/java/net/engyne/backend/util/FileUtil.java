package net.engyne.backend.util;

import java.io.*;

public class FileUtil {

    public static String read(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        fr.close();
        return builder.toString();
    }

    public static void write(String path, String data) throws IOException {
        File file = new File(path);
        if (!file.isFile()) {
            boolean b = file.createNewFile();
            if (!b) {
                throw new IOException("fail to create new file: " + path);
            }
        }
        FileWriter fw = new FileWriter(path);
        fw.write(data);
        fw.flush();
        fw.close();
    }

    public static String getExtension(String path) {
        int index = path.lastIndexOf(".");
        return index < 0 ? "" : path.substring(index, path.length());
    }
}
