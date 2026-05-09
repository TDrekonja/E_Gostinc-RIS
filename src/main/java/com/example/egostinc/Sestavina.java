package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.Sestavina.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.Sestavina
 ***********************************************************************/

/** @pdOid 2272f8b2-7905-4638-8e2f-7195135b7ce6 */
public class Sestavina {
   /** @pdOid 8a8b0500-d7d8-444b-9f19-1144e86bc8be */
   private int Sifrasestavine;
   /** @pdOid 54efbf32-f950-4f31-9a40-f9660087933a */
   private String Naziv;
   /** @pdOid be91eb97-c6aa-4430-8c64-280761d94312 */
   private float Trenutnazaloga;
   /** @pdOid 5436868c-30b5-4e46-b89a-e0879aac347e */
   private float Mejaopozorila;

   public Sestavina(int sifra, String naziv, float zaloga, float meja) {
      this.Sifrasestavine = sifra;
      this.Naziv = naziv;
      this.Trenutnazaloga = zaloga;
      this.Mejaopozorila = meja;
   }
   public int getSifra() {
      return this.Sifrasestavine;
   }
   public float VrniTrenutnoZalogo() {
      return this.Trenutnazaloga;
   }
   
   /** @pdOid 7f6d055c-2066-43e6-adc4-595551132948 */
   public void PosodobiKolicino(float kolicina) {
      this.Trenutnazaloga += kolicina;
   }
   
   /** @pdOid 03a911d5-d069-4bd3-82cf-bc0c3049d706 */
   public float PreveriMejoOpozorila() {
      return this.Mejaopozorila;
   }

}