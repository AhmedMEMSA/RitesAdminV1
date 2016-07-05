package amemsa.ritesadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Manager extends AppCompatActivity {

    private EditText targetID;
    private EditText riteNum;
    private EditText groupNum;
    private EditText delayTime;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Control Panel");
        targetID = (EditText) findViewById(R.id.target_id);
        riteNum = (EditText) findViewById(R.id.rite_num);
        groupNum = (EditText) findViewById(R.id.group_num);
        delayTime = (EditText) findViewById(R.id.delay_time);
        start = (Button) findViewById(R.id.start);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.findfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = targetID.getText().toString();
                getFriendLocation(id);

                Snackbar.make(view, "Please wait. \n  Working on it....", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rn = riteNum.getText().toString();
                String gn = groupNum.getText().toString();
                String dt = delayTime.getText().toString();

                start(rn,gn,dt);

            }
        });
    }
    public void start(String riteNum,String groupNum,String delay) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/control/go";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("rite", riteNum).
                appendQueryParameter("gnum", groupNum).
                appendQueryParameter("wait", delay).
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void getFriendLocation(final String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/selectlocation";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", id).
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("========================");
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            Double lat = jsonObject.getDouble("lat");
                            Double lng = jsonObject.getDouble("lng");
                            Intent i = new Intent(Manager.this,MapsActivity.class);
                            i.putExtra("lat", lat);
                            i.putExtra("lng", lng);
                            i.putExtra("id", id);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jsonObject);
                        System.out.println(jsonObject.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
