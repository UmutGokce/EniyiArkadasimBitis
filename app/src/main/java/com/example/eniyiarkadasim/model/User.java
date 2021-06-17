package com.example.eniyiarkadasim.model;

public class User {

    private String isim;
    private String telefon;
    private String profil_resmi;
    private String seviye;
    private String user_id;

    public User(String isim, String telefon, String profil_resmi, String seviye, String user_id) {
        this.isim = isim;
        this.telefon = telefon;
        this.profil_resmi = profil_resmi;
        this.seviye = seviye;
        this.user_id = user_id;
    }

    public User() {

    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getProfil_resmi() {
        return profil_resmi;
    }

    public void setProfil_resmi(String profil_resmi) {
        this.profil_resmi = profil_resmi;
    }

    public String getSeviye() {
        return seviye;
    }

    public void setSeviye(String seviye) {
        this.seviye = seviye;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
