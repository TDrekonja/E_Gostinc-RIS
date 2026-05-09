package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.K_Prodaja.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.K_Prodaja
 ***********************************************************************/

import java.util.*;

/** @pdOid 89e15c97-f26b-48cb-8896-3bd32f06a7ae */
public class K_Prodaja {
    private List<Artikel> razpolozljiviArtikli = new ArrayList<>();
    private List<Artikel> artikliNaRacunu = new ArrayList<>();

    public K_Prodaja() {
        // 1. Definicija Sestavin (Zaloga v skladišču)
        // com.example.egostinc.Sestavina(Naziv, TrenutnaZaloga)
        Sestavina sodLasko30L = new Sestavina(1, "Pivo Laško točeno Zlatorog sod 30L", 2.0f, 1f);
        Sestavina plocevinkaCola = new Sestavina(2, "Coca Cola ploč 0,33L", 48.0f, 24f);
        Sestavina steklenicaJager = new Sestavina(3, "Jagermeister (35%) 1L", 5.0f, 1f);
        Sestavina steklenicaJack = new Sestavina(4, "Whisky Jack Daniels 1L", 3.0f, 1f);
        Sestavina steklenicaVodka = new Sestavina(5, "Vodka Gorbatschov 1L", 4.0f, 1.5f);
        Sestavina plastenkaPomaranca = new Sestavina(6, "Nektar pomaranča Fructal 1L", 10.0f, 4f);
        Sestavina plocevinkaRedBull = new Sestavina(7, "Red Bull ploč 0,25L", 24.0f, 12f);
        Sestavina plastenkaRadenska = new Sestavina(8, "Voda Radenska gazirana 1,5L", 12.0f, 4f);
        Sestavina steklenicaGin = new Sestavina(9, "Gin Finsbury 1L", 3.0f, 1f);

        // 2. Definicija Artiklov (Tisto, kar vidi natakar na com.example.egostinc.ZM_Blagajna)

        // com.example.egostinc.Artikel(Naziv, Cena)

        // --- PIVO ---
        Artikel lasko05 = new Artikel(1, "Laško točeno 0,5 L", 3.5f,"Pivo");
        lasko05.addNormativ(new Normativ(sodLasko30L, 1.0f / 60.0f)); // 1/60 soda za 0.5L
        this.razpolozljiviArtikli.add(lasko05);

        Artikel lasko03 = new Artikel(2, "Laško točen 0,3 L", 2.8f,"Pivo");
        lasko03.addNormativ(new Normativ(sodLasko30L, 1.0f / 100.0f)); // 1/100 soda za 0.3L
        this.razpolozljiviArtikli.add(lasko03);

        // --- ŽGANJE ---
        Artikel jagerShot = new Artikel(3, "Jägermeister 0,03", 3.2f,"Žgane pijače");
        jagerShot.addNormativ(new Normativ(steklenicaJager, 0.03f)); // Poraba v litrih
        this.razpolozljiviArtikli.add(jagerShot);

        Artikel jackShot = new Artikel(4, "Jack Daniel's 0,03", 4.0f,"Žgane pijače");
        jackShot.addNormativ(new Normativ(steklenicaJack, 0.03f));
        this.razpolozljiviArtikli.add(jackShot);

        // --- MIKSANI NAPITKI (Več normativov) ---
        Artikel jagerCola = new Artikel(5, "Jäger Cola", 5.5f,"Žgane pijače");
        jagerCola.addNormativ(new Normativ(steklenicaJager, 0.03f));
        jagerCola.addNormativ(new Normativ(plocevinkaCola, 0.6f)); // Porabi 60% pločevinke
        this.razpolozljiviArtikli.add(jagerCola);

        Artikel rbVodka = new Artikel(6, "Red bull vodka", 6.5f,"Žgane pijače");
        rbVodka.addNormativ(new Normativ(steklenicaVodka, 0.03f));
        rbVodka.addNormativ(new Normativ(plocevinkaRedBull, 1.0f)); // Celoten Red Bull
        this.razpolozljiviArtikli.add(rbVodka);

        // --- BREZALKOHOLNO ---
        Artikel colaPlocevinka = new Artikel(7, "Coca Cola 0,33", 2.6f,"Brezalkoholno");
        colaPlocevinka.addNormativ(new Normativ(plocevinkaCola, 1.0f));
        this.razpolozljiviArtikli.add(colaPlocevinka);

        Artikel juice02 = new Artikel(8, "Juice 0,2", 2.2f,"Brezalkoholno");
        juice02.addNormativ(new Normativ(plastenkaPomaranca, 0.2f));
        this.razpolozljiviArtikli.add(juice02);
    }
   /** @pdOid 64e72fc9-09cc-4912-92bf-3ff937d7a7a6 */
   public boolean DodajArtikel(Artikel artikel) {
      for(Normativ n: artikel.VrniSeznamNormativov()) {
          boolean resnica = PreveriZalogo(n.getSestavina());
          if(resnica==false) {
              return false;
          }
      }
      for(Normativ n: artikel.VrniSeznamNormativov()){
          OdstejPoNormativu(n);
      }
      artikliNaRacunu.add(artikel);
      return true;
   }
    public void OdstraniArtikel(Artikel artikel) {
        artikliNaRacunu.remove(artikel);
    }
    public List<Artikel> getRazpolozljiviArtikli() {
        return this.razpolozljiviArtikli;
    }
   /** @pdOid 758e0d39-6fd5-40f1-b346-d58db726e2a3
    če je v zalogi nad mejo*/
   public boolean PreveriZalogo(Sestavina sestavina) {
      if(sestavina.VrniTrenutnoZalogo()> sestavina.PreveriMejoOpozorila())
          return true;
      return false;
   }
   
   /** @pdOid be4d4b51-8f16-4110-a49f-0eac2a952a26 */
   public void OdstejPoNormativu(Normativ normativ) {
      normativ.getSestavina().PosodobiKolicino(-(normativ.VrniPotrebnoKolicino()));
   }
   
   /** @pdOid 5dd26bc2-df06-4757-8356-f28ea2629931 */
   public void ZakljuciRacun() {
      // TODO: implement
   }

}