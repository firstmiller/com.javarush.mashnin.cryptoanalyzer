package com.javarush.mashnin.cryptoanalyzer.service;

import com.javarush.mashnin.cryptoanalyzer.exception.NotValidKeyException;
public class CaesarCipher {
    private final char[] alphabet;
    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
    }
    public String applyCipher(String text, int key) {
        if(key <= -alphabet.length || key >= alphabet.length || key == 0)
            throw new NotValidKeyException(key);
        StringBuilder stringBuilder = new StringBuilder();
        for(char symbol: text.toCharArray()) {
            int pos = indexOf(alphabet, symbol);
            if(pos >= 0) {
                int newIndex = (pos + key + alphabet.length) % alphabet.length;
                stringBuilder.append(alphabet[newIndex]);
            }
            else
                stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
    private int indexOf(char[] array, char target) {
        for(int i = 0; i < array.length; i++) {
            if (array[i] == target)
                return i;
        }
        return -1;
    }
}
