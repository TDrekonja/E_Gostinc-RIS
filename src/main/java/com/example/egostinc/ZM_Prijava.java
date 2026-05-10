package com.example.egostinc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ZM_Prijava {

    @FXML private TextField     poljeUporabniskoIme;
    @FXML private PasswordField poljeGeslo;
    @FXML private Label         labelNapaka;

    @FXML
    public void PrijavaVSistem() {
        String ime   = poljeUporabniskoIme.getText().trim();
        String geslo = poljeGeslo.getText();

        Uporabnik uporabnik = Uporabnik.prijava(ime, geslo);

        if (uporabnik == null) {
            labelNapaka.setText("Napačno uporabniško ime ali geslo.");
            labelNapaka.setVisible(true);
            labelNapaka.setManaged(true);
            poljeGeslo.clear();
            return;
        }

        odprijBlagajno(uporabnik);
    }

    private void odprijBlagajno(Uporabnik uporabnik) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(loader.load(), 960, 640);

            ZM_Blagajna blagajna = loader.getController();
            blagajna.setPrijavljenUporabnik(uporabnik);

            Stage stage = new Stage();
            stage.setTitle("E-Gostinc: Blagajna — " +
                    uporabnik.getImeInPriimek() + " (" + uporabnik.vlogaBesedilo() + ")");
            stage.setScene(scene);
            stage.show();

            Stage loginStage = (Stage) poljeUporabniskoIme.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            labelNapaka.setText("Napaka pri odpiranju blagajne.");
            labelNapaka.setVisible(true);
            labelNapaka.setManaged(true);
        }
    }
}
