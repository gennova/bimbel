package com.aie.bimbelaie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Registrasi extends AppCompatActivity {
    LoginHelper helper;
    Button registrasi;
    EditText nama,jenjang,hp,mapel,harijam,alamat,jenisbimbel,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        helper=new LoginHelper(Registrasi.this);
        registrasi=findViewById(R.id.ButtonRegistrasiMuridID);
        nama=findViewById(R.id.namaMuridID);
        jenjang=findViewById(R.id.jenjangMuridID);
        hp=findViewById(R.id.whatsappMuridID);
        mapel=findViewById(R.id.pelajaranMuridID);
        harijam=findViewById(R.id.jamMuridID);
        alamat=findViewById(R.id.alamatMuridID);
        jenisbimbel=findViewById(R.id.jenisLesMuridID);
        email=findViewById(R.id.emailMuridID);
        password=findViewById(R.id.passwordMuridID);
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrasiMurid();
            }
        });
    }

    private void RegistrasiMurid() {
        final String namamurid = nama.getText().toString().trim();
        final String jenjangmurid = jenjang.getText().toString().trim();
        final String hpmurid = hp.getText().toString().trim();
        final String mapelmurid = mapel.getText().toString().trim();
        final String harijammurid = harijam.getText().toString().trim();
        final String alamatmurid = alamat.getText().toString().trim();
        final String jenisbimbelmurid = jenisbimbel.getText().toString().trim();
        final String emailmurid = email.getText().toString().trim();
        final String passwd = password.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegistrasiMuridAPI.REGISTRASIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RegistrasiMuridAPI api = retrofit.create(RegistrasiMuridAPI.class);
        Call<String> call = api.getRegistrasiMurid(namamurid,jenjangmurid,hpmurid,mapelmurid,harijammurid,alamatmurid,jenisbimbelmurid,emailmurid,passwd);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Respon register", response.body().toString());
                //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Registrasi.this,Login.class);
                            startActivity(intent);
                            Registrasi.this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //    Log.i("onEmptyResponse", "Returned empty response");
                        Toast.makeText(getApplicationContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something error responses "+t.getMessage(),Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }
        });

    }
}