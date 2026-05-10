package com.example.egostinc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***********************************************************************
 * Module:  com.example.egostinc.Racun.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.Racun
 ***********************************************************************/


public class Racun {

   public enum NacinPlacila { GOTOVINA, KARTICA }

   private static int stevilcnik = 1;

   private static final List<Racun> arhiv = new ArrayList<>();

   private int stevilkaRacuna;
   private Date datum;
   private List<Postavka> postavke = new ArrayList<>();
   private float skupniZnesek = 0;
   private boolean zakljucen = false;
   private NacinPlacila  nacinPlacila   = null;
   private String        natakar        = "";

   public Racun() {
      this.stevilkaRacuna = stevilcnik++;
      this.datum = new Date();
   }

   public static class Postavka {
      private Artikel artikel;
      private int kolicina;

      public Postavka(Artikel artikel) {
         this.artikel = artikel;
         this.kolicina = 1;
      }

      public void povecajKolicino() {
         this.kolicina++;
      }

      public String getNaziv()    { return artikel.VrniNaziv(); }
      public int    getKolicina() { return kolicina; }
      public float  getCena()     { return artikel.VrniCeno() * kolicina; }
      public Artikel getArtikel() { return artikel; }
   }
   
   /** @pdOid 72b0521e-c0c2-4893-a1ad-05c9f43c9db3 */
   public void DodajPostavko(Artikel artikel) {
      for (Postavka p : postavke) {
         if (p.getArtikel() == artikel) {
            p.povecajKolicino();
            return;
         }
      }
      postavke.add(new Postavka(artikel));
   }
   
   /** @pdOid d7457127-3865-4a47-8c1a-1bf7f2e01eef */
   public float NastaviKoncniZnesek() {
      skupniZnesek = 0;
      for (Postavka p : postavke) {
         skupniZnesek += p.getCena();
      }
      return skupniZnesek;
   }
   
   /** @pdOid b6075393-9523-4102-b4d2-05ca2134db76 */
   public void ArhivirajRacun() {
      if (this.zakljucen) return;
      this.zakljucen = true;
      arhiv.add(this);
      System.out.println("Račun #" + stevilkaRacuna + " arhiviran."+nacinPlacila+" . "+ skupniZnesek + " EUR");
   }

   public static void resetArhiv() {
      arhiv.clear();
      stevilcnik = 1;
   }

   public String getDatumCas() {
      return new SimpleDateFormat("HH:mm:ss").format(datum);
   }

   public String getNacinPlacilaText() {
      if (nacinPlacila == null) return "—";
      return nacinPlacila == NacinPlacila.GOTOVINA ? "Gotovina" : "Kartica";
   }
   public String getZnesekText() {
      return String.format("%.2f €", skupniZnesek);
   }


   public List<Postavka> getPostavke()       { return postavke; }
   public float          getSkupniZnesek()   { return skupniZnesek; }
   public int            getStevilkaRacuna() { return stevilkaRacuna; }
   public boolean        isZakljucen()       { return zakljucen; }
   public NacinPlacila   getNacinPlacila()    { return nacinPlacila; }
   public String         getNatakar()         { return natakar; }
   public Date           getDatum()           { return datum; }

   public void setNacinPlacila(NacinPlacila n) { this.nacinPlacila = n; }
   public void setNatakar(String ime)           { this.natakar = ime; }

   public static List<Racun> getArhiv() { return arhiv; }
}