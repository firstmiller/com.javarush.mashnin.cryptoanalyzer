package com.javarush.mashnin.cryptoanalyzer.controller;

import com.javarush.mashnin.cryptoanalyzer.constant.Alphabet;
import com.javarush.mashnin.cryptoanalyzer.service.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class StatisticAnalysisController {
    StatAnalysis statAnalysis = new StatAnalysis(Alphabet.ALPHABET);
    private File fileFromAnalysis;
    private File fileToAnalysis;
    @FXML
    private Text pathFromAnalysis;
    @FXML
    private Text pathToAnalysis;
    @FXML
    private Text errorAnalysis;
    @FXML
    private Text keyAnalysis;

    @FXML
    protected void onChooseFileToButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileToAnalysis = fileChooser.showOpenDialog(null);
        if (fileToAnalysis != null) {
            pathToAnalysis.setText(fileToAnalysis.getPath());
        } else
            pathToAnalysis.setText("Файл, в который сохранить результат дешифровки");
    }

    @FXML
    protected void onChooseFileFromButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileFromAnalysis = fileChooser.showOpenDialog(null);
        if (fileFromAnalysis != null) {
            pathFromAnalysis.setText(fileFromAnalysis.getPath());
        } else
            pathFromAnalysis.setText("txt файл, содержимое которого нужно расшифровать");
    }

    @FXML
    protected void onAnalysisButtonClick() {
        try {
            int key = statAnalysis.applyAnalysis(fileFromAnalysis, fileToAnalysis);
            keyAnalysis.setText("Возможный ключ: " + key);
            errorAnalysis.setText("Выполнено!");
        } catch (IllegalArgumentException ex) {
            errorAnalysis.setText(ex.getMessage());
        } catch (IOException e) {
            errorAnalysis.setText("При чтении/записи произошла ошибка");
        }

    }
}
