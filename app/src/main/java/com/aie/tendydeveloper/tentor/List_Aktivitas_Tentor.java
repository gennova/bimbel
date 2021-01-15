package com.aie.tendydeveloper.tentor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

public class List_Aktivitas_Tentor extends AppCompatActivity {
    ArrayAdapter<String> adapter_murid;
    List<Map<String, Object>> list_murid = new ArrayList<>();
    EditText filtermurid;
    LoginHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper=new LoginHelper(List_Aktivitas_Tentor.this);
        setContentView(R.layout.activity_list__aktivitas__tentor);
        getResponseMurid(this);

    }
    private void getResponseMurid(Activity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LISTAKTIVITASAPI.PROFILURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        LISTAKTIVITASAPI api = retrofit.create(LISTAKTIVITASAPI.class);
        Call<String> call = api.getAktivitasTentor(helper.getUSERNAME());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Respon Murid", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadProductToArrayMurid(jsonresponse, List_Aktivitas_Tentor.this);
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
                    item.put("HARI", dataobj.getString("hari"));
                    item.put("TANGGAL", dataobj.getString("tanggal"));
                    item.put("DURASI", dataobj.getString("durasi"));
                    item.put("TOPIK", dataobj.getString("topik"));
                    list_murid.add(item);
                }
            }
            //getResponseProduct();
            ListView lv;
            // Listview Data
            final String products[] = new String[list_murid.size()];
            for (int i=0;i<list_murid.size();i++){
                // products[i] = list_tentors.get(i).get("ID").toString()+" - "+list_tentors.get(i).get("NAMA").toString();
                products[i] = list_murid.get(i).get("HARI").toString()+ " "+list_murid.get(i).get("TANGGAL").toString()
                        + " "+list_murid.get(i).get("DURASI").toString() + " "+list_murid.get(i).get("TOPIK").toString();
            }
            lv = (ListView) findViewById(R.id.list_view_product);
            filtermurid = findViewById(R.id.inputSearchProduct);

            // Adding items to listview
            adapter_murid = new ArrayAdapter<String>(this, R.layout.list_item_murid, R.id.product_name, products);
            lv.setAdapter(adapter_murid);
            filtermurid.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    List_Aktivitas_Tentor.this.adapter_murid.getFilter().filter(cs);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}