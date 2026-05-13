package com.example.egostinc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ZM_Placilo {

    @FXML private Label labelZnesek;
    @FXML private Label labelRacun;

    private Racun racun;
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
        String nacinBesedilo = nacin == Racun.NacinPlacila.GOTOVINA
                ? "Gotovina" : "Kartica";
        StringBuilder sb = new StringBuilder();
        for (Racun.Postavka p : racun.getPostavke()) {
            sb.append(String.format("%-22s x%d = %.2f €%n",
                    p.getNaziv(), p.getKolicina(), p.getCena()));
        }
        sb.append(String.format("%n══════════════════════════%n"));
        sb.append(String.format("Plačilo: %s%n", nacinBesedilo));
        sb.append(String.format("SKUPAJ:  %.2f €", racun.getSkupniZnesek()));

        Alert potrdilo = AlertHelper.ustvari(
                Alert.AlertType.INFORMATION,
                "Plačilo uspešno",
                "Račun #" + racun.getStevilkaRacuna() + " — " + nacinBesedilo,
                sb.toString());
        potrdilo.showAndWait();

        if (onPlacanoCallback != null) onPlacanoCallback.run();
    }

    private void zapriOkno() {
        ((Stage) labelZnesek.getScene().getWindow()).close();
    }
}
