package com.javarush.mashnin.cryptoanalyzer.exception;

public class NotValidKeyException extends IllegalArgumentException {
    private int key;
    public NotValidKeyException(int key) {
        super("The key is not valid");
        this.key = key;
    }
    @Override
    public String toString() {
        return String.format("NotValidKeyException[%d] : %s", key, getMessage());
    }
}
