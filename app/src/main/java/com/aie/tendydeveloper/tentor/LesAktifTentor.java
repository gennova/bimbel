package com.aie.tendydeveloper.tentor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LesAktifTentor extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ArrayAdapter<String> adapter;
    TextView namalogin,logout;
    EditText nama,username,hari,tanggal,topik,durasi,namamurid;
    Button submitpresensi;
    LoginHelper helper;
    ArrayAdapter<String> adapter_murid;
    List<Map<String, Object>> list_murid = new ArrayList<>();
    EditText filtermurid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_aktif_tentor);
        helper= new LoginHelper(LesAktifTentor.this);
        nama=findViewById(R.id.namaTentorPresensi);
        username=findViewById(R.id.usernameTentorPresensi);
        hari=findViewById(R.id.hariPresensi);
        tanggal=findViewById(R.id.tanggalPresensi);
        topik=findViewById(R.id.topikPresensi);
        durasi=findViewById(R.id.durasiPresensi);
        submitpresensi=findViewById(R.id.submitPresensi);
        nama.setText(helper.getNAMA());
        username.setText(helper.getUSERNAME());
        namamurid=findViewById(R.id.namamuridTentor);
        namamurid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseMurid(LesAktifTentor.this);
                }
            }
        });
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        hari.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialogHari(LesAktifTentor.this);
                }
            }
        });
        tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog();
                }
            }
        });
        submitpresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresensiTentor();
            }
        });
    }
    public void showDialogHari(Activity activity){
        //getResponseProduct();
        ListView lv;
        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_list_status);
        //setContentView(R.layout.dialog_listview);
        // Listview Data
        final String products[] = {"SENIN", "SELASA", "RABU", "KAMIS", "JUMAT",
                "SABTU","MINGGU"};

        lv = (ListView) dialog.findViewById(R.id.list_view_product);
        //filter = (EditText) dialog.findViewById(R.id.inputSearchProduct);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_status, R.id.product_name, products);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString()+list_products.size(),Toast.LENGTH_LONG).show();
                hari.setText(parent.getItemAtPosition(position).toString());
                hari.clearFocus();
                dialog.dismiss();

            }
        });
        dialog.show();
    }
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void PresensiTentor() {
        final String emailtentor = username.getText().toString().trim();
        final String hariles = hari.getText().toString().trim();
        final String tanggalles = tanggal.getText().toString().trim();
        final String topikles = topik.getText().toString().trim();
        final String durasiles = durasi.getText().toString().trim();
        final String namasiswa = namamurid.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PresensiAPI.PRESENSIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        PresensiAPI api = retrofit.create(PresensiAPI.class);
        Call<String> call = api.postPresensiTentor(emailtentor,hariles,tanggalles,topikles,durasiles,namasiswa);
        Log.i("PRESENSI TENTOR",emailtentor+hariles+tanggalles+topikles+durasiles);
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
                            Intent intent= new Intent(LesAktifTentor.this, TentorActivity.class);
                            startActivity(intent);
                            LesAktifTentor.this.finish();
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

    private void getResponseMurid(Activity activity){
        final int iduser=Integer.parseInt(helper.getIdUser());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MURIDTENTOR.PROFILURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MURIDTENTOR api = retrofit.create(MURIDTENTOR.class);
        Call<String> call = api.getMuridTentor(iduser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Respon Murid", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadProductToArrayMurid(jsonresponse, LesAktifTentor.this);
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
                    item.put("NAMA", dataobj.getString("namamuridnya"));
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
                    LesAktifTentor.this.adapter_murid.getFilter().filter(cs);
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
                    namamurid.setText(code);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}