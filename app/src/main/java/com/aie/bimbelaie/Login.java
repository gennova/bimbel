package com.aie.bimbelaie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aie.bimbelaie.admin.AdminActivity;
import com.aie.bimbelaie.murid.MuridActivity;
import com.aie.bimbelaie.tentor.TentorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {
    Button login;
    LoginHelper helper;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginui);
        helper= new LoginHelper(Login.this);
        login = findViewById(R.id.loginmember);
        username=findViewById(R.id.usernameID);
        password=findViewById(R.id.passwordID);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser() {
        final String uname = username.getText().toString().trim();
        final String passwd = password.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginAPI.LOGINURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<String> call = api.getUserLogin(uname,passwd);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Respon login", response.body().toString());
                //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //      Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body();
                        parseLoginData(jsonresponse);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonresponse);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
    //menyimpan data ke sharepref dan buka intent baru
    private void parseLoginData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                saveInfo(response);
                Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                if(helper.getLEVEL().equals("ADMIN")){
                    Intent intent = new Intent(Login.this, AdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else if(helper.getLEVEL().equals("MURID")){
                    Intent intent = new Intent(Login.this, MuridActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else if(helper.getLEVEL().equals("TENTOR")){
                    Intent intent = new Intent(Login.this, TentorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveInfo(String response){
        helper.setLOGIN(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    helper.setUSERNAME(dataobj.getString("username"));
                    helper.setNAMA(dataobj.getString("nama"));
                    helper.setIdUser(dataobj.getString("id"));
                    helper.setLEVEL(dataobj.getString("level"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}