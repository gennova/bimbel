package com.aie.tendydeveloper;

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

public class RegistrasiTentor extends AppCompatActivity {
    LoginHelper helper;
    Button registrasi;
    EditText nama,hptentornya,jenjang,mapel,harijam,kisaran,alamat,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_tentor);
        helper=new LoginHelper(RegistrasiTentor.this);
        registrasi=findViewById(R.id.registrasiTentorID);
        nama=findViewById(R.id.namatentorID);
        hptentornya=findViewById(R.id.HPtentorID);
        jenjang=findViewById(R.id.jenjangTentorID);
        mapel=findViewById(R.id.mapeltentorID);
        harijam=findViewById(R.id.harijamtentorID);
        kisaran=findViewById(R.id.kisaranhargatentorID);
        alamat=findViewById(R.id.kotatentorID);
        email=findViewById(R.id.usernameTentorID);
        password=findViewById(R.id.passwordtentorID);
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrasiTentor();
            }
        });
    }

    private void RegistrasiTentor() {
        final String namatentor = nama.getText().toString().trim();
        final String hp = hptentornya.getText().toString().trim();
        final String jenjangtentor = jenjang.getText().toString().trim();
        final String mapeltentor = mapel.getText().toString().trim();
        final String harijamtentor = harijam.getText().toString().trim();
        final String kisarantentor = kisaran.getText().toString().trim();
        final String alamattentor = alamat.getText().toString().trim();
        final String emailtentor = email.getText().toString().trim();
        final String passwd = password.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegistrasiTentorAPI.REGISTRASIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RegistrasiTentorAPI api = retrofit.create(RegistrasiTentorAPI.class);
        Call<String> call = api.getRegistrasiMurid(namatentor,hp,jenjangtentor,mapeltentor,harijamtentor,kisarantentor,alamattentor,emailtentor,passwd);
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
                            Intent intent= new Intent(RegistrasiTentor.this,Login.class);
                            startActivity(intent);
                            RegistrasiTentor.this.finish();
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