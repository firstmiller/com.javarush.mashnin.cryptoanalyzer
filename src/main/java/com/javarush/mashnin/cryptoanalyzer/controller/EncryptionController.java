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

public class EncryptionController {
    private File fileFromEncrypt;
    private File fileToEncrypt;
    private File fileFromDecrypt;
    private File fileToDecrypt;
    private CaesarCipher caesarCipher = new CaesarCipher(Alphabet.ALPHABET);

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();

    @FXML
    private TextField keyEncrypt;
    @FXML
    private TextField keyDecrypt;
    @FXML
    private Text keyText;
    @FXML
    private Text pathFromCrypted;
    @FXML
    private Text pathToCrypted;
    @FXML
    private Text pathFromDecrypted;
    @FXML
    private Text pathToDecrypted;
    @FXML
    private Text errorСrypted;
    @FXML
    private Text errorDecrypted;


    public void initialize() {
        keyText.setText(String.format("Целое число, входящее в промежуток [1;%s]", Alphabet.ALPHABET.length - 1));
    }

    @FXML
    protected void onChooseFileEncryptFromButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileFromEncrypt = fileChooser.showOpenDialog(null);
        if (fileFromEncrypt != null) {
            pathFromCrypted.setText(fileFromEncrypt.getPath());
        } else
            pathFromCrypted.setText("txt файл для шифрования");
    }

    @FXML
    protected void onChooseFileEncryptToButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileToEncrypt = fileChooser.showOpenDialog(null);
        if (fileToEncrypt != null) {
            pathToCrypted.setText(fileToEncrypt.getPath());
        } else
            pathToCrypted.setText("txt файл, в который записать зашифрованное сообщение");
    }

    @FXML
    protected void onChooseFileDecryptFromButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileFromDecrypt = fileChooser.showOpenDialog(null);
        if (fileFromDecrypt != null) {
            pathFromDecrypted.setText(fileFromDecrypt.getPath());
        } else
            pathFromCrypted.setText("txt файл для шифрования");
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
    protected void onEncryptButtonClick() {
        try {
            validator.pathsForEncryptIsValid(fileFromEncrypt, fileToEncrypt);
            validator.keyIsNumber(keyEncrypt.getText());
            String pathFrom = fileFromEncrypt.toString();
            String pathTo = fileToEncrypt.toString();
            validator.fileIsNotEmpty(pathFrom);
            int key = Integer.parseInt(this.keyEncrypt.getText());
            fileManager.applyCipherToFile(pathFrom, pathTo, caesarCipher, key);
            errorСrypted.setText("Содержимое файла успешно зашифровано!");
        }
        catch (NotValidKeyException ex) {
            errorСrypted.setText("Значение ключа должно входить в указанный диапазон!");
        }
        catch (IllegalArgumentException ex) {
            errorСrypted.setText(ex.getMessage());
        }
        catch (IOException e) {
            errorСrypted.setText("При чтении/записи произошла ошибка");
        }
    }
    @FXML
    protected void onDecryptButtonClick() {
        try {
            validator.pathsForEncryptIsValid(fileFromDecrypt, fileToDecrypt);
            validator.keyIsNumber(keyDecrypt.getText());
            String pathFrom = fileFromDecrypt.toString();
            String pathTo = fileToDecrypt.toString();
            validator.fileIsNotEmpty(pathFrom);
            int key = Integer.parseInt(this.keyDecrypt.getText());
            fileManager.applyCipherToFile(pathFrom, pathTo, caesarCipher, -key);
            errorDecrypted.setText("Содержимое файла успешно расшифровано!");
        }
        catch (NotValidKeyException ex) {
            errorDecrypted.setText("Значение ключа должно входить в указанный диапазон!");
        }
        catch (IllegalArgumentException ex) {
            errorDecrypted.setText(ex.getMessage());
        }
        catch (IOException e) {
            errorDecrypted.setText("При чтении/записи произошла ошибка");
        }
    }
}
