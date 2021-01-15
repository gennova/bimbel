package com.aie.tendydeveloper.admin;

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
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AdminDetailTentor extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    List<Map<String, Object>> list_tentors = new ArrayList<>();
    ArrayAdapter<String> adapter_tentor;
    EditText filtertentors,tentor,tglawal,tglakhir;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_tentor);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tentor=findViewById(R.id.namaDetailTentor);
        tglawal=findViewById(R.id.tglAwalID);
        tglakhir=findViewById(R.id.tglAkhirID);
        next=findViewById(R.id.buttonDetailID);
        tglawal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog();
                }
            }
        });
        tglakhir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDateDialog2();
                }
            }
        });
        tentor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseTentor(AdminDetailTentor.this);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDetailTentor.this,AktivitasTentor.class);
                intent.putExtra("NAMATENTOR",tentor.getText().toString());
                intent.putExtra("TGLAWAL",tglawal.getText().toString());
                intent.putExtra("TGLAKHIR",tglakhir.getText().toString());
                startActivity(intent);
            }
        });
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
                        loadProductToArray(jsonresponse,AdminDetailTentor.this);
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
                    AdminDetailTentor.this.adapter_tentor.getFilter().filter(cs);
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
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tglawal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void showDateDialog2(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tglakhir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}