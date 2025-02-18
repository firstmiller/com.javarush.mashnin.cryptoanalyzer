package com.javarush.mashnin.cryptoanalyzer.controller;

import com.javarush.mashnin.cryptoanalyzer.constant.Alphabet;
import com.javarush.mashnin.cryptoanalyzer.service.BruteForce;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class BruteForceController {
    private BruteForce bruteForce = new BruteForce(Alphabet.ALPHABET);
    private File fileFromBruteForce;
    private File selectedDirectory;
    @FXML
    private Text pathFromBruteForce;
    @FXML
    private Text pathToBruteForce;
    @FXML
    private Text errorBruteForce;

    @FXML
    protected void onChooseFileBruteForceFromButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileFromBruteForce = fileChooser.showOpenDialog(null);
        if (fileFromBruteForce != null) {
            pathFromBruteForce.setText(fileFromBruteForce.getPath());
        } else
            pathFromBruteForce.setText("txt файл для шифрования");
    }

    @FXML
    protected void onChooseDirectoryBruteForceFromButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            pathToBruteForce.setText(selectedDirectory.getAbsolutePath());
        } else {
            pathToBruteForce.setText("Выберите папку для шифрования");
        }
    }

    @FXML
    protected void onBruteForceButtonClick() {
        try {
            bruteForce.applyBruteForce(fileFromBruteForce, selectedDirectory);
            errorBruteForce.setText("Выполнено!");
        } catch (IllegalArgumentException ex) {
            errorBruteForce.setText(ex.getMessage());
        } catch (IOException ex) {
            errorBruteForce.setText("Произошла ошибка при чтении/записи");
        }
    }
}
