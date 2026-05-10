package com.example.egostinc;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ZM_DnevniPromet implements Initializable {

    @FXML private TableView<Racun>             tabelaRacunov;
    @FXML private TableColumn<Racun, String>   stolpecSt;
    @FXML private TableColumn<Racun, String>   stolpecCas;
    @FXML private TableColumn<Racun, String>   stolpecNatakar;
    @FXML private TableColumn<Racun, String>   stolpecNacin;
    @FXML private TableColumn<Racun, String>   stolpecZnesek;

    @FXML private Label labelSkupajDan;
    @FXML private Label labelGotovina;
    @FXML private Label labelKartica;
    @FXML private Label labelStRacunov;

    private ObservableList<Racun> seznam = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stolpecSt.setCellValueFactory(
                data -> new SimpleStringProperty(String.valueOf(data.getValue().getStevilkaRacuna())));
        stolpecSt.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty ? null : "#" + v);
                setAlignment(Pos.CENTER);
            }
        });

        stolpecCas.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDatumCas()));
        stolpecCas.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty ? null : v);
                setAlignment(Pos.CENTER);
            }
        });

        stolpecNatakar.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNatakar()));

        stolpecNacin.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNacinPlacilaText()));

        stolpecZnesek.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getZnesekText()));
        stolpecZnesek.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty ? null : v);
                setAlignment(Pos.CENTER_RIGHT);
                if (!empty) setStyle("-fx-font-weight: bold; -fx-text-fill: #2c4a7c;");
            }
        });

        tabelaRacunov.setItems(seznam);
        osvezi();
    }

    @FXML
    public void osvezi() {
        seznam.clear();
        seznam.addAll(Racun.getArhiv());

        float skupaj   = 0;
        float gotovina = 0;
        float kartica  = 0;

        for (Racun r : Racun.getArhiv()) {
            skupaj += r.getSkupniZnesek();
            if (r.getNacinPlacila() == Racun.NacinPlacila.GOTOVINA)
                gotovina += r.getSkupniZnesek();
            else if (r.getNacinPlacila() == Racun.NacinPlacila.KARTICA)
                kartica += r.getSkupniZnesek();
        }

        labelSkupajDan.setText(String.format("Skupaj: %.2f €", skupaj));
        labelGotovina.setText(String.format("%.2f €", gotovina));
        labelKartica.setText(String.format("%.2f €", kartica));
        labelStRacunov.setText(String.valueOf(Racun.getArhiv().size()));
    }
}
