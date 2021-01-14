package com.aie.bimbelaie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.aie.bimbelaie.LoginHelper;
import com.aie.bimbelaie.R;

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
    ArrayAdapter<String> adapter;
    EditText statusles,nama,email,namatentor,jadwal,tarif;
    List<Map<String, Object>> list_tentors = new ArrayList<>();
    ArrayAdapter<String> adapter_tentor;
    Button aktivasi;
    LoginHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_presensi_tentor);
        helper=new LoginHelper(DaftarPresensiTentor.this);
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
                list_tentors = new ArrayList<>();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    Map<String, Object> item = new HashMap<>();
                    item.put("HARI", dataobj.getString("hari"));
                    item.put("TANGGAL", dataobj.getString("tanggal"));
                    item.put("DURASI", dataobj.getString("durasi"));
                    list_tentors.add(item);
                }
            }
            //getResponseProduct();
            ListView lv;
            //setContentView(R.layout.dialog_listview);
            // Listview Data
            final String products[] = new String[list_tentors.size()];
            for (int i=0;i<list_tentors.size();i++){
                // products[i] = list_tentors.get(i).get("ID").toString()+" - "+list_tentors.get(i).get("NAMA").toString();
                products[i] = list_tentors.get(i).get("HARI").toString()+" "+list_tentors.get(i).get("TANGGAL").toString()+" "+list_tentors.get(i).get("DURASI").toString();
            }
            lv = (ListView) findViewById(R.id.listpresensiID);
            // Adding items to listview
            adapter_tentor = new ArrayAdapter<String>(this, R.layout.list_item_presensi, R.id.aktivitasID, products);
            lv.setAdapter(adapter_tentor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}