package com.example.realtimelocation;

public class Traking {
    private String email, uid, lat, lang;

    public Traking() {
    }

    public Traking(String email, String uid, String lat, String lang) {
        this.email = email;
        this.uid = uid;
        this.lat = lat;
        this.lang = lang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}