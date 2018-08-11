/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author crysi
 */
public class FileIO {

    public File getFileContent(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName + ".txt");
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        return file;
    }

    public void createFile(String fileName, List<String> build) throws IOException {
        BufferedWriter finalWriter;
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            finalWriter = new BufferedWriter(writer);
            for (int i = 0; i < build.size(); i++) {
                finalWriter.write(build.get(i));
                finalWriter.newLine();
            }
            finalWriter.close();
        }

    }
}
