package com.javarush.mashnin.cryptoanalyzer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class Validator {
    public void keyIsNumber(String userKey) {
        try {
            Integer.parseInt(userKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("Введенный ключ должен быть числом!");
        }
    }

    public void pathsForEncryptDecryptIsValid(Object file1, Object file2) {
        if (file1 == null || file2 == null)
            throw new IllegalArgumentException("Не выбран путь!");
        String path1 = file1.toString();
        String path2 = file2.toString();
        if (!(path1.endsWith("txt") && path2.endsWith("txt")))
            throw new IllegalArgumentException("Путь должен содержать файл с форматом txt!");
        if (path1.equals(path2))
            throw new IllegalArgumentException("Пути не могут совпадать!");
        if (!(Files.isReadable(Path.of(path1)) && Files.isWritable(Path.of(path2))))
            throw new IllegalArgumentException("Файлы по указанному пути должны существовать и доступны для чтения/записи!");
    }

    public void fileIsNotEmpty(String path) throws IOException {
        if (Files.size(Path.of(path)) == 0)
            throw new IllegalArgumentException("Указан путь к пустому файлу");
    }
}
