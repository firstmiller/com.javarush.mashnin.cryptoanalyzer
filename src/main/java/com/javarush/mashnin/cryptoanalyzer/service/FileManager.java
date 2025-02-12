package com.javarush.mashnin.cryptoanalyzer.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public void applyCipherToFile(String inputPath, String outputPath, CaesarCipher caesarCipher, int key) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(inputPath));
             BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(outputPath))) {
            char[] buffer = new char[1024];
            int count;
            while ((count = bufferedReader.read(buffer)) != -1) {
                String subString = new String(buffer, 0, count);
                String cryptedString = caesarCipher.applyCipher(subString, key);
                bufferedWriter.write(cryptedString);
            }
        }
    }
}
