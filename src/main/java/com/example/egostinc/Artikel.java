package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.Artikel.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.Artikel
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;

/** @pdOid ea0eb7c4-3ad1-4d99-9e7c-7191ebfacd63 */
public class Artikel {
   /** @pdOid 5b9ba072-09cb-40ba-8915-957ae48876cc */
   private int Sifraartikla;
   /** @pdOid 98df9dcd-844e-4d4b-999d-a7191f64e046 */
   private String Naziv;
   /** @pdOid 9ca2b785-bd85-48a4-af0f-f3893d6a8fcd */
   private float Cena;
   private String Skupina;
   private List<Normativ> normativi = new ArrayList<>();

   public Artikel(int sifra, String naziv, float cena, String skupina) {
      this.Sifraartikla = sifra;
      this.Naziv = naziv;
      this.Cena = cena;
      this.Skupina= skupina;
   }
   public void addNormativ(Normativ n) {
      if (n != null) {
         this.normativi.add(n);
      }
   }

   public float VrniCeno() {
      return this.Cena;
   }
   public String VrniNaziv() {
      return this.Naziv;
   }
   public String VrniSkupino() {
      return this.Skupina;
   }
   /** @pdOid 6750e834-b6b3-4841-81ad-a70e79ba2966 */
   public void VrniPodatke() {
      // TODO: implement
   }
   
   /** @pdOid 39b8b899-6722-48ed-a8fc-b788096954f9 */
   public List<Normativ> VrniSeznamNormativov() {
      return this.normativi;
   }

}