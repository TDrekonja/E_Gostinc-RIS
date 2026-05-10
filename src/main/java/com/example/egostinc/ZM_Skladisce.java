package com.example.egostinc;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ZM_Skladisce implements Initializable {

    @FXML private TableView<Sestavina>            tabelaSestavin;
    @FXML private TableColumn<Sestavina, Integer> stolpecSifra;
    @FXML private TableColumn<Sestavina, String>  stolpecNaziv;
    @FXML private TableColumn<Sestavina, Float>   stolpecZaloga;
    @FXML private TableColumn<Sestavina, Float>   stolpecMeja;
    @FXML private TableColumn<Sestavina, String>  stolpecStatus;

    private ObservableList<Sestavina> seznam = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stolpecSifra.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getSifra()).asObject());

        stolpecNaziv.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getNaziv()));

        stolpecZaloga.setCellValueFactory(
                data -> new SimpleFloatProperty(data.getValue().VrniTrenutnoZalogo()).asObject());
        stolpecZaloga.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Float v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || v == null) { setText(null); return; }
                setText(String.format("%.2f", v));
                setAlignment(Pos.CENTER_RIGHT);
            }
        });

        stolpecMeja.setCellValueFactory(
                data -> new SimpleFloatProperty(data.getValue().getMejaOpozorila()).asObject());
        stolpecMeja.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(Float v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || v == null) { setText(null); return; }
                setText(String.format("%.2f", v));
                setAlignment(Pos.CENTER_RIGHT);
            }
        });
        stolpecStatus.setCellValueFactory(
                data -> new SimpleStringProperty(""));  // vrednost ni važna, jo ignoriramo v cellFactory
        stolpecStatus.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null); setStyle(""); return;
                }
                Sestavina s = getTableView().getItems().get(getIndex());
                float zaloga = s.VrniTrenutnoZalogo();
                float meja   = s.getMejaOpozorila();

                String tekst; String barva;
                if (zaloga <= 0) {
                    tekst = "● ZMANJKALO";  barva = "#c0392b";
                } else if (zaloga <= meja) {
                    tekst = "● Nizka";      barva = "#e67e22";
                } else {
                    tekst = "● OK";         barva = "#27ae60";
                }
                setText(tekst);
                setStyle("-fx-text-fill: " + barva + "; -fx-font-weight: bold; -fx-font-size: 12px;");
                setAlignment(Pos.CENTER);
            }
        });

        tabelaSestavin.setItems(seznam);
        seznam.addAll(K_Prodaja.getInstance().getSestavljene());
    }

    @FXML
    public void osvezi() {
        seznam.clear();
        seznam.addAll(K_Prodaja.getInstance().getSestavljene());
    }
}
