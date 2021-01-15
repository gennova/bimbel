package com.aie.tendydeveloper;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper {
    private final String idUser ="id";
    private final String LOGIN = "LOGIN";
    private final String USERNAME = "USERNAME";
    private final String NIP = "NIP";
    private final String NAMA = "NAMA";
    private final String TTL = "TTL";
    private final String ALAMAT = "ALAMAT";
    private final String HANDPHONE = "HANDPHONE";
    private final String LOGINSTATE = "LOGINSTATE";
    private final String LEVEL ="LEVEL";
    private SharedPreferences app_prefs;
    private Context context;

    public LoginHelper(Context context){
        app_prefs = context.getSharedPreferences("logged",context.MODE_PRIVATE);
        this.context=context;
    }

    public void setLOGIN (boolean state){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(LOGIN, state);
        edit.apply();
    }

    public boolean getLOGIN() {
        return app_prefs.getBoolean(LOGIN, false);
    }

    public void  setUSERNAME(String username){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USERNAME,username);
        edit.apply();
    }

    public String getUSERNAME() {
        return  app_prefs.getString(USERNAME,"");
    }

    public void setNIP(String nip){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NIP,nip);
        edit.apply();
    }

    public String getNIP(){
        return app_prefs.getString(NIP,"");
    }

    public void setTTL(String ttl){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(TTL,ttl);
        edit.apply();
    }

    public String getTTL(){
        return app_prefs.getString(TTL,"");
    }

    public void setNAMA(String nama){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAMA,nama);
        edit.apply();
    }

    public String getNAMA(){
        return app_prefs.getString(NAMA,"");
    }

    public void setALAMAT(String alamat){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ALAMAT,alamat);
        edit.apply();
    }

    public String getALAMAT(){
        return app_prefs.getString(ALAMAT,"");
    }

    public void setIdUser(String id){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(idUser,id);
        edit.apply();
    }

    public String getIdUser(){
        return app_prefs.getString(idUser,"");
    }

    public void setHANDPHONE(String hp){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(HANDPHONE,hp);
        edit.apply();
    }

    public String getHANDPHONE(){
        return app_prefs.getString(HANDPHONE,"");
    }

    public void setLOGINSTATE (String state){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(LOGINSTATE,state);
        edit.apply();
    }

    public String getLOGINSTATE(){
        return app_prefs.getString(LOGINSTATE,"");
    }

    public void setLEVEL(String level){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(LEVEL,level);
        edit.apply();
    }

    public String getLEVEL() {
        return app_prefs.getString(LEVEL,"");
    }
}
