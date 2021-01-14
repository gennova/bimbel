package com.aie.bimbelaie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aie.bimbelaie.Login;
import com.aie.bimbelaie.LoginHelper;
import com.aie.bimbelaie.R;
import com.aie.bimbelaie.Registrasi;
import com.aie.bimbelaie.RegistrasiMuridAPI;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AktivasiTentor extends AppCompatActivity {
    EditText nama,email;
    LoginHelper helper;
    Button terima,tolak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_tentor);
        helper=new LoginHelper(AktivasiTentor.this);
        nama=findViewById(R.id.namatentorbaruID);
        email=findViewById(R.id.emailtentorbaruID);
        terima=findViewById(R.id.terimaID);
        tolak=findViewById(R.id.tolakID);
        email.setText(getIntent().getExtras().getString("email","defaultKey"));
        nama.setText(getIntent().getExtras().getString("nama","defaultKey"));
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrasiMurid("AKTIF");
            }
        });
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrasiMurid("DITOLAK");
            }
        });
    }
    private void RegistrasiMurid(String status) {
        final String namatentor = nama.getText().toString().trim();
        final String emailtentor = email.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AktivasiTENTORAPI.AKTIVASIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        AktivasiTENTORAPI api = retrofit.create(AktivasiTENTORAPI.class);
        Call<String> call = api.postAktivasiTentor(namatentor,emailtentor,status);
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
                            Intent intent= new Intent(AktivasiTentor.this, AdminActivity.class);
                            startActivity(intent);
                            AktivasiTentor.this.finish();
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