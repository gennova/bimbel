package com.aie.tendydeveloper.tentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.MainActivity;
import com.aie.tendydeveloper.R;

public class TentorActivity extends AppCompatActivity {
    LoginHelper helper;
    TextView nama,logout;
    ImageView profil,lesaktif,presensi,aktivitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentor);
        helper=new LoginHelper(TentorActivity.this);
        nama=findViewById(R.id.textNameLogin);
        logout=findViewById(R.id.logoutIDStaff);
        nama.setText(helper.getNAMA());
        profil=findViewById(R.id.profilTentorID);
        presensi=findViewById(R.id.presensiTentorHarian);
        aktivitas = findViewById(R.id.daftaraktivitasID);
        aktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TentorActivity.this,List_Aktivitas_Tentor.class);
                startActivity(intent);
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TentorActivity.this,ProfilTentor.class);
                startActivity(intent);
            }
        });
        presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TentorActivity.this,LesAktifTentor.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }
    private void Logout(){
        helper.setLOGIN(false);
        helper.setLEVEL("");
        helper.setNAMA("");
        helper.setUSERNAME("");
        helper.setLOGINSTATE("");
        helper.setIdUser("");
        Intent intent=new Intent(TentorActivity.this, MainActivity.class);
        startActivity(intent);
        TentorActivity.this.finish();
    }
}