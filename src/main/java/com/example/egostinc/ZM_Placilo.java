package com.example.egostinc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ZM_Placilo {

    @FXML private Label labelZnesek;
    @FXML private Label labelRacun;

    private Racun racun;
    // Callback — pokliče se ko je plačilo izbrano
    private Runnable onPlacanoCallback;

    public void setRacun(Racun racun, Runnable onPlacano) {
        this.racun             = racun;
        this.onPlacanoCallback = onPlacano;
        labelZnesek.setText(String.format("%.2f €", racun.getSkupniZnesek()));
        labelRacun.setText("Račun #" + racun.getStevilkaRacuna());
    }

    @FXML
    public void platiGotovina() {
        zakljuci(Racun.NacinPlacila.GOTOVINA);
    }

    @FXML
    public void platiKartica() {
        zakljuci(Racun.NacinPlacila.KARTICA);
    }

    @FXML
    public void preklic() {
        zapriOkno();
    }

    private void zakljuci(Racun.NacinPlacila nacin) {
        racun.setNacinPlacila(nacin);
        racun.ArhivirajRacun();
        zapriOkno();
        if (onPlacanoCallback != null) onPlacanoCallback.run();
    }

    private void zapriOkno() {
        ((Stage) labelZnesek.getScene().getWindow()).close();
    }
}
