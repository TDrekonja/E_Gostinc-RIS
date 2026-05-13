package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.ZM_Blagajna.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.ZM_Blagajna
 ***********************************************************************/

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/** @pdOid bb90312e-1cfb-4ba0-8d52-21917f1c3264 */
public class ZM_Blagajna implements Initializable{

   private K_Prodaja kontroler = new K_Prodaja();
   private Racun trenutniRacun = new Racun();
   private Uporabnik prijavljenUporabnik;


   private ObservableList<Racun.Postavka> postavkeRacuna = FXCollections.observableArrayList();


   @FXML private TableView<Racun.Postavka>      tabelaRacuna;
   @FXML private TableColumn<Racun.Postavka, String>  stolpecArtikel;
   @FXML private TableColumn<Racun.Postavka, Integer> stolpecKolicina;
   @FXML private TableColumn<Racun.Postavka, Float>   stolpecCena;
   @FXML private TableColumn<Racun.Postavka, Void>    stolpecOdstrani;

   @FXML private Label  labelSkupaj;
   @FXML private Button gumbIzdajRacun;
   @FXML private Button gumbSkladisce;
   @FXML private Button gumbPromet;
   @FXML private Label  labelUporabnik;

   @FXML private TilePane panePivo;
   @FXML private TilePane paneZgane;
   @FXML private TilePane paneBrezalkoholno;
   @FXML private TilePane paneVse;

   public void setPrijavljenUporabnik(Uporabnik u) {
      this.prijavljenUporabnik = u;

      if (labelUporabnik != null) {
         labelUporabnik.setText(u.getImeInPriimek() + " · " + u.vlogaBesedilo());
      }

      if (gumbSkladisce != null) {
         boolean viden = u.getVloga() == Uporabnik.Vloga.VODJA
                 || u.getVloga() == Uporabnik.Vloga.SKLADISCNIK;
         gumbSkladisce.setVisible(viden);
         gumbSkladisce.setManaged(viden);
      }
      boolean vidnoPromet = u.getVloga() == Uporabnik.Vloga.VODJA;
      if (gumbPromet != null) {
         gumbPromet.setVisible(vidnoPromet);
         gumbPromet.setManaged(vidnoPromet);
      }
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      stolpecArtikel.setCellValueFactory(new PropertyValueFactory<>("naziv"));
      stolpecArtikel.setCellFactory(col -> {
         TableCell<Racun.Postavka, String> cell = new TableCell<>() {
            private final Label lbl = new Label();
            {
               lbl.setWrapText(true);
               lbl.prefWidthProperty().bind(col.widthProperty().subtract(12));
               lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #3a4a5c;");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
               super.updateItem(item, empty);
               if (empty || item == null) { setGraphic(null); return; }
               lbl.setText(item);
               setGraphic(lbl);
               setPadding(new Insets(4, 6, 4, 6));
            }
         };
         return cell;
      });

      stolpecKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
      stolpecKolicina.setCellFactory(col -> new TableCell<>() {
         @Override protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) { setText(null); return; }
            setText(String.valueOf(item));
            setAlignment(Pos.CENTER);
            setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #3a4a5c;");
         }
      });

      stolpecCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
      stolpecCena.setCellFactory(col -> new TableCell<>() {
         @Override protected void updateItem(Float item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) { setText(null); return; }
            setText(String.format("%.2f", item));
            setAlignment(Pos.CENTER_RIGHT);
            setStyle("-fx-font-size: 12px; -fx-text-fill: #2c4a7c; -fx-font-weight: bold;");
         }
      });

      nastavljGumbZaBrisanje();
      tabelaRacuna.setItems(postavkeRacuna);
      tabelaRacuna.setFixedCellSize(-1);

      osveziGumbePoSkupini("Pivo",          panePivo);
      osveziGumbePoSkupini("Žgane pijače",  paneZgane);
      osveziGumbePoSkupini("Brezalkoholno", paneBrezalkoholno);
      osveziGumbePoSkupini("Vse",           paneVse);
   }

   private void nastavljGumbZaBrisanje() {
      if (stolpecOdstrani == null) return;
      stolpecOdstrani.setCellFactory(new Callback<>() {
         @Override
         public TableCell<Racun.Postavka, Void> call(TableColumn<Racun.Postavka, Void> col) {

            return new TableCell<>() {
               private final Button btn = new Button("✕");

               {
                  btn.getStyleClass().add("gumb-odstrani");

                  btn.setOnAction(e -> {
                     Racun.Postavka postavka = getTableView().getItems().get(getIndex());
                     odstraniPostavko(postavka);
                  });
               }

               @Override
               protected void updateItem(Void item, boolean empty) {
                  super.updateItem(item, empty);
                  if (empty) {
                     setGraphic(null);
                  } else {
                     setAlignment(Pos.CENTER);
                     setGraphic(btn);
                  }
               }
            };
         }
      });
   }
   private void odstraniPostavko(Racun.Postavka postavka) {

      postavkeRacuna.remove(postavka);
      trenutniRacun.getPostavke().remove(postavka);
      PrikaziOsvezenRacun();
   }


   /*private void osveziGumbeArtiklov() {
      paneGumbi.getChildren().clear();

      for (Artikel art : kontroler.getRazpolozljiviArtikli()) {
         Button btn = new Button(art.VrniNaziv() + "\n" + art.VrniCeno() + " €");
         btn.setPrefSize(150, 80);
         btn.setWrapText(true);
         btn.setOnAction(e -> {
            klikNaArtikel(art);
         });

         paneGumbi.getChildren().add(btn);
      }
   }*/
   private void osveziGumbePoSkupini(String izbranaSkupina, TilePane ciljniPane) {
      if (ciljniPane == null) return;
      ciljniPane.getChildren().clear();

      for (Artikel art : kontroler.getRazpolozljiviArtikli()) {
         if (izbranaSkupina.equals("Vse") || art.VrniSkupino().equals(izbranaSkupina)) {
            Button btn = new Button(art.VrniNaziv() + "\n" + String.format("%.2f €", art.VrniCeno()));
            btn.setPrefSize(150, 76);
            btn.setWrapText(true);
            btn.getStyleClass().add("gumb-artikel");
            btn.setOnAction(e -> klikNaArtikel(art));
            ciljniPane.getChildren().add(btn);
         }
      }
   }

   private void klikNaArtikel(Artikel art) {
      boolean uspeh = kontroler.DodajArtikel(art);

      if (!uspeh) {
         PrikaziOpozoriloZaloge();
         return;
      }

      for (Normativ n : art.VrniSeznamNormativov()) {
         kontroler.OdstejPoNormativu(n);
      }

      trenutniRacun.DodajPostavko(art);
      boolean zeBilo = false;
      for (Racun.Postavka p : postavkeRacuna) {
         if (p.getArtikel() == art) {
            zeBilo = true;
            break;
         }
      }
      if (!zeBilo) {
          postavkeRacuna.add(new Racun.Postavka(art));
      } else {
         tabelaRacuna.refresh();
      }

      PrikaziOsvezenRacun();
   }

   /** @pdOid bee3c6b7-e276-4281-bbaf-cc70c35b7b8b */
   public void PrikaziOpozoriloZaloge() {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Opozorilo o zalogi");
      alert.setHeaderText("Manjka sestavina!");
      alert.setContentText("Na zalogi ni dovolj surovin za ta artikel.");
      alert.showAndWait();
   }
   
   /** @pdOid 9445f33b-f8a5-427e-90e2-fea5bc166305 */
   public void PregledPrometa() {
      // TODO: implement
   }
   
   /** @pdOid ef3880b9-d0b8-4871-ace6-c6e6dd069db4 */
   public void IzberiArtikle() {
      // TODO: implement
   }
   
   /** @pdOid 4e0e8424-1ace-4b7a-af6b-278daeb5b645 */
   public void PrijavaVSistem() {
      // TODO: implement
   }
   
   /** @pdOid 1315b0b5-20ac-4c58-9601-e66417d422c8 */
   public void IzdajRacun() {
      if (postavkeRacuna.isEmpty()) {
         Alert a = new Alert(Alert.AlertType.WARNING);
         a.setTitle("Prazen račun");
         a.setHeaderText("Na računu ni artiklov.");
         a.setContentText("Najprej dodajte vsaj en artikel.");
         a.showAndWait();
         return;
      }
      trenutniRacun.NastaviKoncniZnesek();

      odpriPlacilo();
   }
   private void odpriPlacilo() {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("placilo-view.fxml"));
         Scene scene = new Scene(loader.load());

         ZM_Placilo ctrl = loader.getController();
         ctrl.setRacun(trenutniRacun, this::poPlacilu);

         Stage stage = new Stage();
         stage.setTitle("Plačilo");
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setResizable(false);
         stage.setScene(scene);
         stage.showAndWait();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public void poPlacilu() {
      PrikaziKoncniRacun();
   }
   /** @pdOid 1fe42668-4615-4814-994e-7b7c74eae933 */
   public void PrikaziKoncniRacun() {
      trenutniRacun = new Racun();
      if (prijavljenUporabnik != null)
         trenutniRacun.setNatakar(prijavljenUporabnik.getImeInPriimek());
      postavkeRacuna.clear();
      PrikaziOsvezenRacun();
   }
   
   /** @pdOid 6428cefe-9c18-4872-8282-536bf6b20cbf */
   public void PrikaziOsvezenRacun() {
      float skupaj = 0;
      for (Racun.Postavka p : postavkeRacuna) {
         skupaj += p.getCena();
      }
      labelSkupaj.setText(String.format("Skupaj: %.2f €", skupaj));
   }
   @FXML
   public void odpriSkladisce() {
      try {
         Stage stage = new Stage();
         stage.setTitle("E-Gostinc: Pregled skladišča");
         stage.initModality(Modality.NONE);
         stage.setScene(new Scene(
                 new FXMLLoader(getClass().getResource("skladisce-view.fxml")).load(),
                 800, 480));
         stage.show();
      } catch (Exception e) { e.printStackTrace(); }
   }

   @FXML
   public void odpriPromet() {
      try {
         Stage stage = new Stage();
         stage.setTitle("E-Gostinc: Dnevni promet");
         stage.initModality(Modality.NONE);
         stage.setScene(new Scene(
                 new FXMLLoader(getClass().getResource("promet-view.fxml")).load(),
                 680, 480));
         stage.show();
      } catch (Exception e) { e.printStackTrace(); }
   }

   public void odjava() {
      try {
         FXMLLoader loader = new FXMLLoader(
                 getClass().getResource("login-view.fxml"));
         Scene scene = new Scene(loader.load(), 400, 500);
         Stage stage = new Stage();
         stage.setTitle("E-Gostinc — Prijava");
         stage.setResizable(false);
         stage.setScene(scene);
         stage.show();

         K_Prodaja.resetInstance();

         Stage blagajnaStage = (Stage) labelSkupaj.getScene().getWindow();
         blagajnaStage.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}