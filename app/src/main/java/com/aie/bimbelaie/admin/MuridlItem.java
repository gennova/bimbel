package com.aie.bimbelaie.admin;

import java.io.Serializable;

public class MuridlItem implements Serializable {
    String nama, jenjang, mapel,harijam,status,email;

    public MuridlItem(String nama, String jenjang, String mapel,String hari,String status) {
        this.nama = nama;
        this.jenjang = jenjang;
        this.mapel = mapel;
        this.harijam=hari;
        this.status=status;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getMapel() {
        return mapel;
    }

    public void setHarijam(String harijam) {
        this.harijam = harijam;
    }

    public String getHarijam() {
        return harijam;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
