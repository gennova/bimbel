package com.aie.tendydeveloper.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.MainActivity;
import com.aie.tendydeveloper.R;

public class AdminActivity extends AppCompatActivity {
    LoginHelper helper;
    TextView namalogin,logout;
    ImageView murid,tentor,muridbaru,tentorbaru,aktivitas,addles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        murid=findViewById(R.id.muridIDVview);
        tentor=findViewById(R.id.datatentorID);
        muridbaru=findViewById(R.id.siswabaruID);
        tentorbaru=findViewById(R.id.tentorbaruID);
        aktivitas=findViewById(R.id.aktivitastentorID);
        namalogin=findViewById(R.id.textNameLoginAdmin);
        logout=findViewById(R.id.logoutIDStaff);
        addles=findViewById(R.id.addedLestID);
        helper=new LoginHelper(AdminActivity.this);
        namalogin.setText(helper.getNAMA());
        murid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,DaftarSiswa.class);
                startActivity(intent);
            }
        });
        tentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,DaftarTentor.class);
                startActivity(intent);
            }
        });
        muridbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,SiswaBaru.class);
                startActivity(intent);
            }
        });
        tentorbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,TentorBaru.class);
                startActivity(intent);
            }
        });
        aktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(AdminActivity.this,AktivitasTentor.class);
                Intent intent= new Intent(AdminActivity.this,AdminDetailTentor.class);
                startActivity(intent);
            }
        });
        addles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminActivity.this,AdminAddLes.class);
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
        helper.setIdUser("");
        helper.setLOGINSTATE("");
        Intent intent=new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        AdminActivity.this.finish();
    }
}