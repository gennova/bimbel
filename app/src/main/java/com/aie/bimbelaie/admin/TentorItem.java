package com.aie.bimbelaie.admin;

import java.io.Serializable;

public class TentorItem implements Serializable {
    String nama, jenjang, kisaran,kota,email,idtentor;

    public TentorItem(String nama, String jenjang, String kisaran, String kota) {
        this.nama = nama;
        this.jenjang = jenjang;
        this.kisaran = kisaran;
        this.kota = kota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public String getKisaran() {
        return kisaran;
    }

    public void setKisaran(String kisaran) {
        this.kisaran = kisaran;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIdtentor(String idtentor) {
        this.idtentor = idtentor;
    }

    public String getIdtentor() {
        return idtentor;
    }
}
