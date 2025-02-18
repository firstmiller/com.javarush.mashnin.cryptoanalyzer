package com.javarush.mashnin.cryptoanalyzer.service;

import com.javarush.mashnin.cryptoanalyzer.exception.NotValidKeyException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CaesarCipher {
    private final char[] alphabet;
    private Validator validator = new Validator();
    private FileManager fileManager = new FileManager();

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
        Arrays.sort(this.alphabet);
    }

    public String shiftText(String text, int key) {
        if (key <= -alphabet.length || key >= alphabet.length || key == 0)
            throw new NotValidKeyException(key);
        StringBuilder stringBuilder = new StringBuilder();
        for (char symbol : text.toCharArray()) {
            int pos = Arrays.binarySearch(alphabet, symbol);
            if (pos >= 0) {
                int newIndex = (pos + key + alphabet.length) % alphabet.length;
                stringBuilder.append(alphabet[newIndex]);
            } else
                stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }

    public void applyCipher(File inputFile, File outputFile, String keyText, CipherMode cipherMode) throws IOException {
        validator.pathsForEncryptDecryptIsValid(inputFile, outputFile);
        validator.keyIsNumber(keyText);
        String pathFrom = inputFile.toString();
        String pathTo = outputFile.toString();
        validator.fileIsNotEmpty(pathFrom);
        int key = Integer.parseInt(keyText);
        validator.keyIsInRange(key, 1, alphabet.length);
        key = cipherMode == CipherMode.CIPHER ? key : -key;
        fileManager.applyCipherToFile(pathFrom, pathTo, this, key);
    }
}
