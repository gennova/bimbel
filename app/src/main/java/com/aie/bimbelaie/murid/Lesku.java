package com.aie.bimbelaie.murid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aie.bimbelaie.LoginHelper;
import com.aie.bimbelaie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Lesku extends AppCompatActivity {
    LoginHelper helper;
    TextView jadwal,tentor,status,namatentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesku);
        helper=new LoginHelper(Lesku.this);
        jadwal=findViewById(R.id.jadwalLesID);
        tentor=findViewById(R.id.namatentorID);
        status=findViewById(R.id.statuslesID);
        namatentor=findViewById(R.id.namaTentorMuridID);
        ProfilUSERByID();
    }

    private void ProfilUSERByID() {
        final String iduser=helper.getIdUser();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PROFILAPI.PROFILURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        PROFILAPI api = retrofit.create(PROFILAPI.class);
        Call<String> call = api.getProfilSiswa(iduser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.i("Respon les", response.body().toString());
                //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //      Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonresponse);
                            //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                jadwal.setText(dataobj.getString("jadwal"));
                                tentor.setText(dataobj.getString("tentor"));
                                status.setText(dataobj.getString("status"));
                                namatentor.setText(dataobj.getString("namatentor"));
                            }
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