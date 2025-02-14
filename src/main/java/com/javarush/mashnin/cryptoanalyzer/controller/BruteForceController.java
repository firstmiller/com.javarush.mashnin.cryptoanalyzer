package com.javarush.mashnin.cryptoanalyzer.controller;

import com.javarush.mashnin.cryptoanalyzer.constant.Alphabet;
import com.javarush.mashnin.cryptoanalyzer.service.CaesarCipher;
import com.javarush.mashnin.cryptoanalyzer.service.FileManager;
import com.javarush.mashnin.cryptoanalyzer.service.Validator;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class BruteForceController {
    private char[] alphabet = Alphabet.ALPHABET;
    private File fileFromBruteForce;
    private File selectedDirectory;
    private CaesarCipher caesarCipher = new CaesarCipher(alphabet);

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();
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
            validator.pathsForBruteForceIsValid(fileFromBruteForce, selectedDirectory);
            String pathFrom = fileFromBruteForce.toString();
            String fileName = fileFromBruteForce.getName().split(".txt")[0];
            validator.fileIsNotEmpty(pathFrom);
            for (int key = 1; key < alphabet.length; key++) {
                Path directory = Path.of(selectedDirectory.toString());
                Path file = Path.of(String.format("%sKey%d.txt", fileName, key));
                Path pathDirectoryWithFile = directory.resolve(file);
                fileManager.applyCipherToFile(pathFrom, pathDirectoryWithFile.toString(), caesarCipher, -key);
            }
            errorBruteForce.setText("Выполнено!");
        }
        catch (IllegalArgumentException ex) {
            errorBruteForce.setText(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
