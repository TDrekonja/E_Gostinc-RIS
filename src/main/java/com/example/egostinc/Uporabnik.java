package com.example.egostinc;

import java.util.ArrayList;
import java.util.List;


public class Uporabnik {

    public enum Vloga { NATAKAR, VODJA, SKLADISCNIK }

    private String uporabniskoIme;
    private String geslo;
    private String imeInPriimek;
    private Vloga  vloga;

    public Uporabnik(String ime, String geslo, String imeInPriimek, Vloga vloga) {
        this.uporabniskoIme = ime;
        this.geslo          = geslo;
        this.imeInPriimek   = imeInPriimek;
        this.vloga          = vloga;
    }

    public String getUporabniskoIme() { return uporabniskoIme; }
    public String getImeInPriimek()   { return imeInPriimek; }
    public Vloga  getVloga()          { return vloga; }

    public boolean preveriGeslo(String vnosGesla) {
        return this.geslo.equals(vnosGesla);
    }

    private static final List<Uporabnik> SEZNAM = new ArrayList<>();
    static {
        SEZNAM.add(new Uporabnik("natakar",     "1234",  "Ana Novak",     Vloga.NATAKAR));
        SEZNAM.add(new Uporabnik("natakar2",    "1234",  "Bor Krajnc",    Vloga.NATAKAR));
        SEZNAM.add(new Uporabnik("vodja",       "admin", "Cene Horvat",   Vloga.VODJA));
        SEZNAM.add(new Uporabnik("skladiscnik", "5678",  "Deja Mlakar",   Vloga.SKLADISCNIK));
    }

    public static Uporabnik prijava(String ime, String geslo) {
        for (Uporabnik u : SEZNAM) {
            if (u.uporabniskoIme.equals(ime) && u.preveriGeslo(geslo)) {
                return u;
            }
        }
        return null;
    }

    public String vlogaBesedilo() {
        return switch (vloga) {
            case NATAKAR     -> "Natakar";
            case VODJA       -> "Vodja";
            case SKLADISCNIK -> "Skladiščnik";
        };
    }
}
