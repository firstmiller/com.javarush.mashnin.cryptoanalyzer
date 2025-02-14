package com.javarush.mashnin.cryptoanalyzer.controller;

import com.javarush.mashnin.cryptoanalyzer.constant.Alphabet;
import com.javarush.mashnin.cryptoanalyzer.exception.NotValidKeyException;
import com.javarush.mashnin.cryptoanalyzer.service.CaesarCipher;
import com.javarush.mashnin.cryptoanalyzer.service.FileManager;
import com.javarush.mashnin.cryptoanalyzer.service.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class DecryptionController {
    private File fileFromDecrypt;
    private File fileToDecrypt;
    private CaesarCipher caesarCipher = new CaesarCipher(Alphabet.ALPHABET);

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();

    @FXML
    private TextField keyDecrypt;
    @FXML
    private Text keyTextDecrypted;
    @FXML
    private Text pathFromDecrypted;
    @FXML
    private Text pathToDecrypted;
    @FXML
    private Text errorDecrypted;


    public void initialize() {
        keyTextDecrypted.setText(String.format("Целое число, входящее в промежуток [1;%s]", Alphabet.ALPHABET.length - 1));
    }

    @FXML
    protected void onChooseFileDecryptFromButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileFromDecrypt = fileChooser.showOpenDialog(null);
        if (fileFromDecrypt != null) {
            pathFromDecrypted.setText(fileFromDecrypt.getPath());
        } else
            pathFromDecrypted.setText("txt файл для шифрования");
    }

    @FXML
    protected void onChooseFileDecryptToButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileToDecrypt = fileChooser.showOpenDialog(null);
        if (fileToDecrypt != null) {
            pathToDecrypted.setText(fileToDecrypt.getPath());
        } else
            pathToDecrypted.setText("txt файл для шифрования");
    }

    @FXML
    protected void onDecryptButtonClick() {
        try {
            validator.pathsForEncryptDecryptIsValid(fileFromDecrypt, fileToDecrypt);
            validator.keyIsNumber(keyDecrypt.getText());
            String pathFrom = fileFromDecrypt.toString();
            String pathTo = fileToDecrypt.toString();
            validator.fileIsNotEmpty(pathFrom);
            int key = Integer.parseInt(this.keyDecrypt.getText());
            fileManager.applyCipherToFile(pathFrom, pathTo, caesarCipher, -key);
            errorDecrypted.setText("Содержимое файла успешно расшифровано!");
        } catch (NotValidKeyException ex) {
            errorDecrypted.setText("Значение ключа должно входить в указанный диапазон!");
        } catch (IllegalArgumentException ex) {
            errorDecrypted.setText(ex.getMessage());
        } catch (IOException e) {
            errorDecrypted.setText("При чтении/записи произошла ошибка");
        }
    }
}
