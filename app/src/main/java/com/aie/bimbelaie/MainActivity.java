package com.aie.bimbelaie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aie.bimbelaie.admin.AdminActivity;
import com.aie.bimbelaie.murid.MuridActivity;
import com.aie.bimbelaie.tentor.TentorActivity;

public class MainActivity extends AppCompatActivity {
    Button login, registrasi;
    TextView regtentor;
    LoginHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginbutt);
        registrasi = findViewById(R.id.registrasibutt);
        regtentor = findViewById(R.id.registrasitentor);
        helper=new LoginHelper(MainActivity.this);
        if(helper.getLOGIN()){
            if(helper.getLEVEL().equals("ADMIN")){
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }else if(helper.getLEVEL().equals("MURID")){
                Intent intent = new Intent(MainActivity.this, MuridActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }else if(helper.getLEVEL().equals("TENTOR")){
                Intent intent = new Intent(MainActivity.this, TentorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
        }
        regtentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrasiTentor.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registrasi.class);
                startActivity(intent);
            }
        });
    }
}