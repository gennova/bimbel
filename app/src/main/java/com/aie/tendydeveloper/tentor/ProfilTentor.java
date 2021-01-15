package com.aie.tendydeveloper.tentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.MainActivity;
import com.aie.tendydeveloper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProfilTentor extends AppCompatActivity {
    LoginHelper helper;
    TextView namatentor,alamat,jenjang,kisaran,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_tentor);
        helper=new LoginHelper(ProfilTentor.this);
        namatentor=findViewById(R.id.namaProfileTentorID);
        alamat=findViewById(R.id.alamatTentorID);
        jenjang=findViewById(R.id.jenjangTentorID);
        kisaran=findViewById(R.id.kisaranID);
        email=findViewById(R.id.emailTentorID);
        ProfilUSERByID();
    }
    private void Logout(){
        helper.setLOGIN(false);
        helper.setLEVEL("");
        helper.setNAMA("");
        helper.setUSERNAME("");
        helper.setLOGINSTATE("");
        Intent intent=new Intent(ProfilTentor.this, MainActivity.class);
        startActivity(intent);
        ProfilTentor.this.finish();
    }
    private void ProfilUSERByID() {
        final int iduser=Integer.parseInt(helper.getIdUser());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PROFILTENTORAPI.PROFILURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        PROFILTENTORAPI api = retrofit.create(PROFILTENTORAPI.class);
        Call<String> call = api.getProfilTentor(iduser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.i("Respon login", response.body().toString());
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
                                namatentor.setText(dataobj.getString("nama"));
                                alamat.setText(dataobj.getString("alamat"));
                                jenjang.setText(dataobj.getString("jenjang"));
                                kisaran.setText(dataobj.getString("hargaperjam"));
                                email.setText(dataobj.getString("username"));
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