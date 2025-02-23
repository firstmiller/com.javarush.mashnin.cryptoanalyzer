package com.javarush.mashnin.cryptoanalyzer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatAnalysis {
    private final char[] alphabet;
    private static final FileManager fileManager = new FileManager();
    private final Validator validator = new Validator();
    private final CaesarCipher caesarCipher;

    public StatAnalysis(char[] alphabet) {
        this.alphabet = alphabet;
        Arrays.sort(this.alphabet);
        caesarCipher = new CaesarCipher(alphabet);
    }

    public int applyAnalysis(File fileFromAnalysis, File fileToAnalysis) throws IOException {
        validator.pathsForEncryptDecryptIsValid(fileFromAnalysis, fileToAnalysis);
        validator.fileIsNotEmpty(fileFromAnalysis.toString());
        Path sampleFilePath = fileManager.extractResourceToTempFile("sample.txt");
        Map<Character, Double> sampleStat = calcStatistic(sampleFilePath.toString());
        Map<Character, Double> factStat = calcStatistic(fileFromAnalysis.toString());
        int key = statAnalyze(caesarCipher, factStat, sampleStat);
        caesarCipher.applyCipher(fileFromAnalysis, fileToAnalysis, String.valueOf(key), CipherMode.DECIPHER);
        return key;
    }

    private int statAnalyze(CaesarCipher caesarCipher, Map<Character, Double> encryptedFreq, Map<Character, Double> sampleFreq) {
        int bestShift = 0;
        double minMSE = Double.MAX_VALUE;
        int n = alphabet.length;

        for (int shift = 0; shift < n; shift++) {
            double mse = 0;

            for (char ch : alphabet) {
                char shiftedChar = caesarCipher.shiftText(String.valueOf(ch), shift).charAt(0);
                double encryptedCount = encryptedFreq.getOrDefault(shiftedChar, 0.0);
                double sampleCount = sampleFreq.getOrDefault(ch, 0.0);

                mse += Math.pow(encryptedCount - sampleCount, 2);
            }

            if (mse < minMSE) {
                minMSE = mse;
                bestShift = shift;
            }
        }
        return bestShift;
    }

    private Map<Character, Double> calcStatistic(String pathToSampleText) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int totalSymbols = 0;

        try (BufferedReader br = Files.newBufferedReader(Path.of(pathToSampleText))) {
            int count;
            char[] buffer = new char[1024];

            while ((count = br.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    char symbol = buffer[i];
                    if (Arrays.binarySearch(alphabet, symbol) >= 0) {
                        frequencyMap.put(symbol, frequencyMap.getOrDefault(symbol, 0) + 1);
                        totalSymbols++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Character, Double> statisticSample = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            statisticSample.put(entry.getKey(), entry.getValue() / (double) totalSymbols);
        }

        return statisticSample;
    }
}
