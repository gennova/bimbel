package com.aie.tendydeveloper.murid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.MainActivity;
import com.aie.tendydeveloper.R;

public class MuridActivity extends AppCompatActivity {
    LoginHelper helper;
    TextView nama,logout;
    ImageView profil,lesku,listharga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murid);
        helper= new LoginHelper(MuridActivity.this);
        nama=findViewById(R.id.textNameLogin);
        logout=findViewById(R.id.logoutIDStaff);
        nama.setText(helper.getNAMA());
        profil=findViewById(R.id.profilkuID);
        lesku=findViewById(R.id.leskuID);
        listharga=findViewById(R.id.daftarhargaID);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MuridActivity.this,ProfilMurid.class);
                startActivity(intent);
            }
        });
        lesku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MuridActivity.this,Lesku.class);
                startActivity(intent);
            }
        });
        listharga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MuridActivity.this,DaftarHarga.class);
                startActivity(intent);
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
        Intent intent=new Intent(MuridActivity.this, MainActivity.class);
        startActivity(intent);
        MuridActivity.this.finish();
    }
}