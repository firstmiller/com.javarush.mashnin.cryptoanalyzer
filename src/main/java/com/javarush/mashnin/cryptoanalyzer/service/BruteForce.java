package com.javarush.mashnin.cryptoanalyzer.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class BruteForce {
    private final char[] alphabet;
    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();
    private CaesarCipher caesarCipher;
    public BruteForce(char[] alphabet) {
        this.alphabet = alphabet;
        this.caesarCipher = new CaesarCipher(alphabet);
    }
    public void applyBruteForce(File inputFile, File outputDir ) throws IOException {
        validator.pathsForBruteForceIsValid(inputFile, outputDir);
        String pathFrom = inputFile.toString();
        String fileName = inputFile.getName().split(".txt")[0];
        validator.fileIsNotEmpty(pathFrom);
        Path directory = Path.of(outputDir.toString());
        for (int key = 1; key < alphabet.length; key++) {
            Path file = Path.of(String.format("%sKey%d.txt", fileName, key));
            Path pathDirectoryWithFile = directory.resolve(file);
            fileManager.applyCipherToFile(pathFrom, pathDirectoryWithFile.toString(), caesarCipher, -key);
        }
    }
}
