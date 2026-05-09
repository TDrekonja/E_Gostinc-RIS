package com.example.egostinc; /***********************************************************************
 * Module:  com.example.egostinc.Normativ.java
 * Author:  Tine
 * Purpose: Defines the Class com.example.egostinc.Normativ
 ***********************************************************************/

/** @pdOid b866f6dd-15b0-4a5b-8995-a69993b5b27a */
public class Normativ {
   /** @pdOid 226ceb5b-0e63-42b7-97b3-e4abaa771559 */
   private float Kolicina;
   private Sestavina Sestavina;

   public Normativ(Sestavina sestavina, float kolicina){
      this.Sestavina = sestavina;
      this.Kolicina = kolicina;
   }
   public Sestavina getSestavina() {
      return Sestavina;
   }
   
   /** @pdOid 0586ead6-474f-4fda-b702-8b21302cc39d */
   public float VrniPotrebnoKolicino() {
      return this.Kolicina;
   }
   
   /** @pdOid 242eb2f0-a7ea-49b2-b32c-46b9af6ee389 */
   public int VrniIDSestavine() {
      return this.Sestavina.getSifra();
   }

}