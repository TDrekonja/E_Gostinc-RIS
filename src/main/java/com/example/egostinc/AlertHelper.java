package com.example.egostinc;

import javafx.scene.control.Alert;

public class AlertHelper {

    private static final String CSS_PATH =
            AlertHelper.class.getResource("blagajna.css").toExternalForm();

    public static Alert ustvari(Alert.AlertType tip, String naslov,
                                String glava, String vsebina) {
        Alert alert = new Alert(tip);
        alert.setTitle(naslov);
        alert.setHeaderText(glava);
        alert.setContentText(vsebina);
        alert.getDialogPane().getStylesheets().add(CSS_PATH);
        return alert;
    }
}
