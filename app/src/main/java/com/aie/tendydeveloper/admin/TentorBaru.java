package com.aie.tendydeveloper.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aie.tendydeveloper.LoginHelper;
import com.aie.tendydeveloper.PublicURL;
import com.aie.tendydeveloper.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TentorBaru extends AppCompatActivity {
    private static final String JSON_URL = PublicURL.getAPIURL()+"daftartentorbaru.php";
    ListView listView;
    private List<TentorItem> tentorItems;
    LoginHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentor_baru);
        helper = new LoginHelper(TentorBaru.this);
        tentorItems = new ArrayList<>();

        //loadPlayer(); // use this for GET method
        postNewComment(); // use this for POST Method
        listView =  findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TentorItem obj = (TentorItem) parent.getAdapter().getItem(position);
                //Toast.makeText(getApplicationContext(),"Selected "+obj.fname,Toast.LENGTH_LONG).show();
                Log.i("Nama Tentor ",obj.getNama()+obj.getEmail());
                Intent intent= new Intent(TentorBaru.this,AktivasiTentor.class);
                intent.putExtra("nama",obj.getNama());
                intent.putExtra("email",obj.getEmail());
                startActivity(intent);
            }
        });
    }
    public void postNewComment(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET,JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray playerArray = obj.getJSONArray("data");
                    //Toast.makeText(getApplicationContext(),"Lihat data"+playerArray.length(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < playerArray.length(); i++) {
                        JSONObject playerObject = playerArray.getJSONObject(i);
                        TentorItem item = new TentorItem(playerObject.getString("nama"),
                                playerObject.getString("jenjang"),
                                playerObject.getString("hargaperjam"),playerObject.getString("alamat"));
                        item.setEmail(playerObject.getString("username"));
                        tentorItems.add(item);
                    }
                    ListViewTentorAdapter adapter = new ListViewTentorAdapter(tentorItems, getApplicationContext());
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //params.put("jadwalhari", getIntent().getExtras().getString("hari","defaultKey"));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(sr);
    }
}