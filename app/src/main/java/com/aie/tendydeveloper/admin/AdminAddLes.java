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
import android.widget.TextView;
import android.widget.Toast;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.MainActivity;
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

public class AdminAddLes extends AppCompatActivity {
    LoginHelper helper;
    TextView namalogin,logout;
    EditText tentor,murid,jadwal,tarif;
    ArrayAdapter<String> adapter_murid;
    List<Map<String, Object>> list_murid = new ArrayList<>();
    EditText filtermurid;
    List<Map<String, Object>> list_tentors = new ArrayList<>();
    ArrayAdapter<String> adapter_tentor;
    EditText filtertentors;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_les);
        helper=new LoginHelper(AdminAddLes.this);
        logout=findViewById(R.id.logoutIDStaffAdd);
        tentor=findViewById(R.id.namaTentoeIDAdd);
        murid=findViewById(R.id.namaMuridIDAdd);
        jadwal=findViewById(R.id.jadwalLesIDAdd);
        tarif=findViewById(R.id.tarifLesAdd);
        submit=findViewById(R.id.submitADD);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahLest();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        tentor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseTentor(AdminAddLes.this);
                }
            }
        });
        murid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseMurid(AdminAddLes.this);
                }
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
        Intent intent=new Intent(AdminAddLes.this, MainActivity.class);
        startActivity(intent);
        AdminAddLes.this.finish();
    }
    private void getResponseMurid(Activity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MuridAPIList.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        MuridAPIList api = retrofit.create(MuridAPIList.class);
        Call<String> call = api.getString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Respon Murid", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadProductToArrayMurid(jsonresponse,AdminAddLes.this);
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
    private void loadProductToArrayMurid(String response,Activity activity){
        //Toast.makeText(getApplicationContext(),"INI DI LOAD PRODUCT",Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                list_murid = new ArrayList<>();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    Map<String, Object> item = new HashMap<>();
                    item.put("ID", dataobj.getString("id"));
                    item.put("NAMA", dataobj.getString("nama"));
                    list_murid.add(item);
                }
            }
            //getResponseProduct();
            ListView lv;
            final Dialog dialog = new Dialog(activity);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_list_murid);
            //setContentView(R.layout.dialog_listview);
            // Listview Data
            final String products[] = new String[list_murid.size()];
            for (int i=0;i<list_murid.size();i++){
                // products[i] = list_tentors.get(i).get("ID").toString()+" - "+list_tentors.get(i).get("NAMA").toString();
                products[i] = list_murid.get(i).get("NAMA").toString();
            }
            lv = (ListView) dialog.findViewById(R.id.list_view_product);
            filtermurid = dialog.findViewById(R.id.inputSearchProduct);

            // Adding items to listview
            adapter_murid = new ArrayAdapter<String>(this, R.layout.list_item_murid, R.id.product_name, products);
            lv.setAdapter(adapter_murid);
            filtermurid.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    AdminAddLes.this.adapter_murid.getFilter().filter(cs);
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
                    murid.setText(code);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        loadProductToArray(jsonresponse,AdminAddLes.this);
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
                    AdminAddLes.this.adapter_tentor.getFilter().filter(cs);
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
                    tentor.setText(code);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void TambahLest() {
        final String namatentor = tentor.getText().toString().trim();
        final String namamurid = murid.getText().toString().trim();
        final String jadwalnya = jadwal.getText().toString().trim();
        final String tarifnya = tarif.getText().toString().trim();
        final String statusnya = "AKTIF";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AddLesAPI.AKTIVASIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        AddLesAPI api = retrofit.create(AddLesAPI.class);
        Call<String> call = api.tambahles(namatentor,namamurid,jadwalnya,tarifnya,statusnya);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Respon Addles", response.body().toString());
                //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(AdminAddLes.this, AdminActivity.class);
                            startActivity(intent);
                            AdminAddLes.this.finish();
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