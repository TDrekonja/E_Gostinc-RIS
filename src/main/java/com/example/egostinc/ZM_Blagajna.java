package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.ZM_Blagajna.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.ZM_Blagajna
 ***********************************************************************/

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import java.net.URL;
import java.util.ResourceBundle;

/** @pdOid bb90312e-1cfb-4ba0-8d52-21917f1c3264 */
public class ZM_Blagajna implements Initializable{

   private K_Prodaja kontroler = new K_Prodaja();
   private Racun trenutniRacun = new Racun();

   private ObservableList<Racun.Postavka> postavkeRacuna = FXCollections.observableArrayList();


   @FXML private TableView<Racun.Postavka>      tabelaRacuna;
   @FXML private TableColumn<Racun.Postavka, String>  stolpecArtikel;
   @FXML private TableColumn<Racun.Postavka, Integer> stolpecKolicina;
   @FXML private TableColumn<Racun.Postavka, Float>   stolpecCena;
   @FXML private Label  labelSkupaj;
   @FXML private Button gumbIzdajRacun;

   @FXML private TilePane panePivo;
   @FXML private TilePane paneZgane;
   @FXML private TilePane paneBrezalkoholno;
   @FXML private TilePane paneVse;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      stolpecArtikel.setCellValueFactory(new PropertyValueFactory<>("naziv"));
      stolpecKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
      stolpecCena.setCellValueFactory(new PropertyValueFactory<>("cena"));

      tabelaRacuna.setItems(postavkeRacuna);

      osveziGumbePoSkupini("Pivo",          panePivo);
      osveziGumbePoSkupini("Žgane pijače",  paneZgane);
      osveziGumbePoSkupini("Brezalkoholno", paneBrezalkoholno);
      osveziGumbePoSkupini("Vse",           paneVse);
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
      ciljniPane.getChildren().clear();

      for (Artikel art : kontroler.getRazpolozljiviArtikli()) {
         if (izbranaSkupina.equals("Vse") || art.VrniSkupino().equals(izbranaSkupina)) {
            Button btn = new Button(art.VrniNaziv() + "\n" + String.format("%.2f €", art.VrniCeno()));
            btn.setPrefSize(130, 60);
            btn.setWrapText(true);
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

      PrikaziKoncniRacun();
   }
   
   /** @pdOid 1fe42668-4615-4814-994e-7b7c74eae933 */
   public void PrikaziKoncniRacun() {
      StringBuilder sb = new StringBuilder();
      sb.append("Račun #").append(trenutniRacun.getStevilkaRacuna()).append("\n");
      sb.append("─────────────────────\n");

      for (Racun.Postavka p : postavkeRacuna) {
         sb.append(String.format("%-20s x%d = %.2f €%n",
                 p.getNaziv(), p.getKolicina(), p.getCena()));
      }

      sb.append("─────────────────────\n");
      sb.append(String.format("SKUPAJ: %.2f €", trenutniRacun.getSkupniZnesek()));

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Končni račun");
      alert.setHeaderText("Račun #" + trenutniRacun.getStevilkaRacuna());
      alert.setContentText(sb.toString());
      alert.showAndWait();

      trenutniRacun.ArhivirajRacun();

      trenutniRacun = new Racun();
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

}