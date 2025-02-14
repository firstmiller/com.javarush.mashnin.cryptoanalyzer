package com.javarush.mashnin.cryptoanalyzer.service;

import com.javarush.mashnin.cryptoanalyzer.exception.NotValidKeyException;

import java.util.Arrays;

public class CaesarCipher {
    private final char[] alphabet;

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
        Arrays.sort(this.alphabet);
    }

    public String applyCipher(String text, int key) {
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
}
