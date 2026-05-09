package com.example.egostinc;

import java.util.ArrayList;
import java.util.List;

/***********************************************************************
 * Module:  com.example.egostinc.Racun.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.Racun
 ***********************************************************************/


public class Racun {

   private static int stevilcnik = 1;
   private int stevilkaRacuna;
   private java.util.Date datum;
   private List<Postavka> postavke = new ArrayList<>();
   private float skupniZnesek = 0;
   private boolean zakljucen = false;
   private java.util.Date Datum;

   public Racun() {
      this.stevilkaRacuna = stevilcnik++;
      this.datum = new java.util.Date();
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
      this.zakljucen = true;
      System.out.println("Račun #" + stevilkaRacuna + " arhiviran. Skupaj: " + skupniZnesek + " EUR");
   }

   public List<Postavka> getPostavke()       { return postavke; }
   public float          getSkupniZnesek()   { return skupniZnesek; }
   public int            getStevilkaRacuna() { return stevilkaRacuna; }
   public boolean        isZakljucen()       { return zakljucen; }
}