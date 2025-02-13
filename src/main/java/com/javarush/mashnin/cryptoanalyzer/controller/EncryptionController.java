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
    private CaesarCipher caesarCipher = new CaesarCipher(Alphabet.ALPHABET);

    private FileManager fileManager = new FileManager();
    private Validator validator = new Validator();

    @FXML
    private TextField keyEncrypt;
    @FXML
    private Text keyTextEncrypted;
    @FXML
    private Text pathFromCrypted;
    @FXML
    private Text pathToCrypted;
    @FXML
    private Text errorСrypted;

    public void initialize() {
        keyTextEncrypted.setText(String.format("Целое число, входящее в промежуток [1;%s]", Alphabet.ALPHABET.length - 1));
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
    protected void onEncryptButtonClick() {
        try {
            validator.pathsForEncryptDecryptIsValid(fileFromEncrypt, fileToEncrypt);
            validator.keyIsNumber(keyEncrypt.getText());
            String pathFrom = fileFromEncrypt.toString();
            String pathTo = fileToEncrypt.toString();
            validator.fileIsNotEmpty(pathFrom);
            int key = Integer.parseInt(this.keyEncrypt.getText());
            fileManager.applyCipherToFile(pathFrom, pathTo, caesarCipher, key);
            errorСrypted.setText("Содержимое файла успешно зашифровано!");
        } catch (NotValidKeyException ex) {
            errorСrypted.setText("Значение ключа должно входить в указанный диапазон!");
        } catch (IllegalArgumentException ex) {
            errorСrypted.setText(ex.getMessage());
        } catch (IOException e) {
            errorСrypted.setText("При чтении/записи произошла ошибка");
        }
    }
}
