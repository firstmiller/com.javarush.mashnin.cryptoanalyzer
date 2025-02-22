package com.javarush.mashnin.cryptoanalyzer.service;

import com.javarush.mashnin.cryptoanalyzer.App;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

public class FileManager {
    public void applyCipherToFile(String inputPath, String outputPath, CaesarCipher caesarCipher, int key) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(inputPath));
             BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(outputPath))) {
            char[] buffer = new char[1024];
            int count;
            while ((count = bufferedReader.read(buffer)) != -1) {
                String subString = new String(buffer, 0, count);
                String cryptedString = caesarCipher.shiftText(subString, key);
                bufferedWriter.write(cryptedString);
            }
        }
    }
    public Path extractResourceToTempFile(String resourcePath) throws IOException {
        // Используем ClassLoader для поиска файла
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            System.out.println("Файл НЕ найден: " + resourcePath);
            System.out.println("Попробуйте использовать другой путь.");
            throw new FileNotFoundException("Ресурс не найден: " + resourcePath);
        }

        // Выводим все доступные ресурсы (для отладки)
        Enumeration<URL> resources = classLoader.getResources("");
        while (resources.hasMoreElements()) {
            System.out.println("Available resource: " + resources.nextElement());
        }

        // Создаём временный файл и копируем туда содержимое
        Path tempFile = Files.createTempFile("cryptoanalyzer-", ".txt");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }
}
