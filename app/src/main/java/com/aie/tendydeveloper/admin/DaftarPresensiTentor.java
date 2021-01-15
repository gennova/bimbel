package com.aie.tendydeveloper.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DaftarPresensiTentor extends AppCompatActivity {
    TextView namatentor,username,hp,password,jenjang,mapel,hari,harga,alamat;
    ArrayAdapter<String> adapter;
    List<Map<String, Object>> list_tentors = new ArrayList<>();
    ArrayAdapter<String> adapter_tentor;
    Button aktivasi;
    LoginHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_presensi_tentor);
        helper=new LoginHelper(DaftarPresensiTentor.this);
        namatentor=findViewById(R.id.namatentor);
        username=findViewById(R.id.username);
        hp=findViewById(R.id.hptentor);
        password=findViewById(R.id.passwordtentor);
        jenjang=findViewById(R.id.jenjangtentor);
        mapel=findViewById(R.id.mapeltentor);
        hari=findViewById(R.id.harijamtentor);
        harga=findViewById(R.id.hargadiritentor);
        alamat=findViewById(R.id.alamattentor);
        getResponseTentor(DaftarPresensiTentor.this);
    }
    private void getResponseTentor(Activity activity){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TentorAktivitasAPI.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        TentorAktivitasAPI api = retrofit.create(TentorAktivitasAPI.class);
        Call<String> call = api.getAktivitas(getIntent().getExtras().getString("IDTENTOR","defaultKey"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadProductToArray(jsonresponse,DaftarPresensiTentor.this);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    //menyimpan data ke array Product
    private void loadProductToArray(String response,Activity activity){
        //Toast.makeText(getApplicationContext(),"INI DI LOAD PRODUCT",Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject dataobj = dataArray.getJSONObject(0);
                namatentor.setText(dataobj.getString("nama"));
                username.setText(dataobj.getString("username"));
                password.setText(dataobj.getString("password"));
                hp.setText(dataobj.getString("hptentor"));
                jenjang.setText(dataobj.getString("jenjang"));
                harga.setText(dataobj.getString("hargaperjam"));
                mapel.setText(dataobj.getString("mapel"));
                alamat.setText(dataobj.getString("alamat"));
                hari.setText(dataobj.getString("harijam"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}