package com.aie.tendydeveloper.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AktivasiLes extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    EditText statusles,nama,email,namatentor,jadwal,tarif;
    List<Map<String, Object>> list_tentors = new ArrayList<>();
    EditText filtertentors;
    ArrayAdapter<String> adapter_tentor;
    Button aktivasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_les);
        statusles=findViewById(R.id.statusLESID);
        nama=findViewById(R.id.namasiswabaruID);
        email=findViewById(R.id.emailsiswabaruID);
        jadwal=findViewById(R.id.jadwalLESIDTentor);
        tarif=findViewById(R.id.tariflesID);
        email.setText(getIntent().getExtras().getString("email","defaultKey"));
        nama.setText(getIntent().getExtras().getString("nama","defaultKey"));
        namatentor=findViewById(R.id.tentornyaID);
        aktivasi=findViewById(R.id.aktivasiID);
        aktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AktivasiMurid();
            }
        });
        namatentor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseTentor(AktivasiLes.this);
                }
            }
        });
        statusles.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialogPoli(AktivasiLes.this);
                }
            }
        });
    }
    public void showDialogPoli(Activity activity){
        //getResponseProduct();
        ListView lv;
        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_list_status);
        //setContentView(R.layout.dialog_listview);
        // Listview Data
        final String products[] = {"AKTIF", "NON AKTIF", "JADWAL PENUH", "MENUNGGU PERSETUJUAN", "BERHENTI",
                "SELESAI"};

        lv = (ListView) dialog.findViewById(R.id.list_view_product);
        //filter = (EditText) dialog.findViewById(R.id.inputSearchProduct);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_status, R.id.product_name, products);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString()+list_products.size(),Toast.LENGTH_LONG).show();
                statusles.setText(parent.getItemAtPosition(position).toString());
                statusles .clearFocus();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void getResponseTentor(Activity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TentorAPIList.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        TentorAPIList api = retrofit.create(TentorAPIList.class);
        Call<String> call = api.getString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadProductToArray(jsonresponse,AktivasiLes.this);
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
                    item.put("ID", dataobj.getString("id"));
                    item.put("NAMA", dataobj.getString("nama"));
                    list_tentors.add(item);
                }
            }
            //getResponseProduct();
            ListView lv;
            final Dialog dialog = new Dialog(activity);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_list_tentors);
            //setContentView(R.layout.dialog_listview);
            // Listview Data
            final String products[] = new String[list_tentors.size()];
            for (int i=0;i<list_tentors.size();i++){
               // products[i] = list_tentors.get(i).get("ID").toString()+" - "+list_tentors.get(i).get("NAMA").toString();
                products[i] = list_tentors.get(i).get("NAMA").toString();
            }
            lv = (ListView) dialog.findViewById(R.id.list_view_product);
            filtertentors = dialog.findViewById(R.id.inputSearchProduct);

            // Adding items to listview
            adapter_tentor = new ArrayAdapter<String>(this, R.layout.list_item_tentor, R.id.product_name, products);
            lv.setAdapter(adapter_tentor);
            filtertentors.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    AktivasiLes.this.adapter.getFilter().filter(cs);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //makeText(getApplicationContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                    String code = parent.getItemAtPosition(position).toString();
                    namatentor.setText(code);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void AktivasiMurid() {
        final String namamurid = nama.getText().toString().trim();
        final String emailnya = email.getText().toString().trim();
        final String jadwalnya = jadwal.getText().toString().trim();
        final String tarifnya = tarif.getText().toString().trim();
        final String statusnya = statusles.getText().toString().trim();
        final String tentornya = namatentor.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AktivasiAPI.AKTIVASIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        AktivasiAPI api = retrofit.create(AktivasiAPI.class);
        Call<String> call = api.getAktivasiMurid(namamurid,emailnya,jadwalnya,tarifnya,statusnya,tentornya);
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
                            Intent intent= new Intent(AktivasiLes.this, AdminActivity.class);
                            startActivity(intent);
                            AktivasiLes.this.finish();
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