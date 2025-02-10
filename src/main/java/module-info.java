module com.javarush.mashnin.cryptoanalyzer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.javarush.mashnin.cryptoanalyzer to javafx.fxml;
    exports com.javarush.mashnin.cryptoanalyzer;
    exports com.javarush.mashnin.cryptoanalyzer.controller;
    opens com.javarush.mashnin.cryptoanalyzer.controller to javafx.fxml;
}